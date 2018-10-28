package guru.bonacci.oogway.relastic;

import static java.util.UUID.randomUUID;
import static org.elasticsearch.common.xcontent.NamedXContentRegistry.EMPTY;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.xContent;
import static org.elasticsearch.common.xcontent.XContentType.JSON;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Component
public class ElasticAdapter<T extends BaseObject> {

	// works on single index and single type only
	private String index;
    private String type;
	
    private Class<T> genericType;

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    
    @SuppressWarnings("unchecked")
	public ElasticAdapter(RestHighLevelClient client, ObjectMapper objectMapper) {
    	this.client = client;
    	this.objectMapper = objectMapper;
    	
    	//FIXME: does not work yet
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ElasticAdapter.class);
    }
    
    public ElasticAdapter<T> theIndex(String index) {
        this.index = index;
        return this;
    }
    
    public ElasticAdapter<T> theType(String type) {
        this.type = type;
        return this;
    }
    
    public ElasticAdapter<T> theGenericType(Class<T> genericType) {
        Objects.requireNonNull(genericType, "this temporary workaround does not allow null values");
    	this.genericType = genericType;
        return this;
    }
    
	public Mono<T> findById(String id) {
    	return Mono
                .<GetResponse>create(sink ->
                        client.getAsync(new GetRequest(index, type, id), RequestOptions.DEFAULT, listenerToSink(sink))
                )
                .filter(GetResponse::isExists)
                .map(resp -> {
                	T t = objectMapper.convertValue(resp.getSource(), genericType);
                	t.setId(resp.getId());
                	return t;
                });
    }

    public Mono<T> random() {
    	FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(ScoreFunctionBuilders.randomFunction());
    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
    	searchSourceBuilder.query(functionScoreQueryBuilder);
    	return searchOne(searchSourceBuilder);
    }

	public Mono<T> searchOne(SearchSourceBuilder search) {
        return Mono
                .<SearchResponse>create(sink ->
                        client.searchAsync(new SearchRequest(index).types(type).source(search), RequestOptions.DEFAULT, listenerToSink(sink))
                )
                .map(SearchResponse::getHits)
                .filter(hits -> hits.totalHits > 0)
                .map(hits  -> hits.getAt(new Random().nextInt(hits.getHits().length)))
                .map(SearchHit::getSourceAsMap)
                .map(map -> objectMapper.convertValue(map, genericType));
    }
	
	public Flux<T> search(SearchSourceBuilder search) {
       return Flux
                .<SearchResponse>create(sink ->
                        client.searchAsync(new SearchRequest(index).types(type).source(search), RequestOptions.DEFAULT, listenerToSink(sink))
                )
				.map(SearchResponse::getHits)
				.flatMap(hs -> Flux.fromArray(hs.getHits()))
				.map(resp -> {
					T t = objectMapper.convertValue(resp.getSourceAsMap(), genericType);
					t.setId(resp.getId());
					return t;
				});
	}

    public Mono<IndexResponse> indexDoc(T doc) {
        return Mono.create(sink -> {
            try {
                doIndex(doc, listenerToSink(sink));
            } catch (JsonProcessingException e) {
                sink.error(e);
            }
        });
    }

    private void doIndex(T doc, ActionListener<IndexResponse> listener) throws JsonProcessingException {
    	final IndexRequest request = new IndexRequest(index, type, randomUUID().toString());
        final String json = objectMapper.writeValueAsString(doc);
        request.source(json, JSON);
        client.indexAsync(request, RequestOptions.DEFAULT, listener);
    }

    public Mono<UpdateResponse> updateDoc(T doc) {
    	return Mono.create(sink -> {
            try {
                doUpdate(doc, listenerToSink(sink));
            } catch (IOException e) {
                sink.error(e);
            }
        });
    }

    private void doUpdate(T doc, ActionListener<UpdateResponse> listener) throws IOException {
    	XContentParser parser = xContent(JSON).createParser(EMPTY, null, objectMapper.writeValueAsBytes(doc));
    	XContentBuilder json = jsonBuilder().copyCurrentStructure(parser);
        doUpdate(doc.getId(), json, listener);
    }

    public Mono<UpdateResponse> updateDoc(String docId, XContentBuilder json) {
        return Mono.create(sink -> doUpdate(docId, json, listenerToSink(sink)));
    }

    private void doUpdate(String docId, XContentBuilder json, ActionListener<UpdateResponse> listener) {
    	final UpdateRequest request = new UpdateRequest(index, type, docId);
        request.doc(json);
        request.retryOnConflict(5);
        client.updateAsync(request, RequestOptions.DEFAULT, listener);
    }

    public Mono<DeleteResponse> deleteDoc(String docId) {
        return Mono.create(sink -> doDelete(docId, listenerToSink(sink)));
    }

    private void doDelete(String docId, ActionListener<DeleteResponse> listener) {
    	final DeleteRequest request = new DeleteRequest(index, type, docId);
        client.deleteAsync(request, RequestOptions.DEFAULT, listener);
    }


    private <U> ActionListener<U> listenerToSink(MonoSink<U> sink) {
        return new ActionListener<U>() {
            @Override
            public void onResponse(U response) {
                sink.success(response);
            }

            @Override
            public void onFailure(Exception e) {
                sink.error(e);
            }
        };
    }
    
    private <U> ActionListener<U> listenerToSink(FluxSink<U> sink) {
        return new ActionListener<U>() {
            @Override
            public void onResponse(U response) {
            	// not the beautiful reactive stream that we were hoping for...
                sink.next(response);
                sink.complete(); 
            }

            @Override
            public void onFailure(Exception e) {
                sink.error(e);
            }
        };
    }

}
