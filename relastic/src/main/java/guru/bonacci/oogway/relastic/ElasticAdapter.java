package guru.bonacci.oogway.relastic;

import java.io.IOException;
import java.util.UUID;

import org.elasticsearch.action.ActionListener;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Component
public class ElasticAdapter<T> {

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
    
    public ElasticAdapter<T> index(String index) {
        this.index = index;
        return this;
    }
    
    public ElasticAdapter<T> type(String type) {
        this.type = type;
        return this;
    }
    
    public ElasticAdapter<T> genericType(Class<T> genericType) {
        this.genericType = genericType;
        return this;
    }
    
	public Mono<T> findById(String id) {
    	return Mono
                .<GetResponse>create(sink ->
                        client.getAsync(new GetRequest(index, type, id), RequestOptions.DEFAULT, listenerToSink(sink))
                )
                .filter(GetResponse::isExists)
                .map(GetResponse::getSource)
                .map(map -> objectMapper.convertValue(map, genericType));
    }

    public Mono<T> random() {
    	FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(QueryBuilders.matchAllQuery(), ScoreFunctionBuilders.randomFunction());
    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
    	searchSourceBuilder.query(functionScoreQueryBuilder);
    	return search(searchSourceBuilder);
    }

	public Mono<T> search(SearchSourceBuilder search) {
        return Mono
                .<SearchResponse>create(sink ->
                        client.searchAsync(new SearchRequest(index).types(type).source(search), RequestOptions.DEFAULT, listenerToSink(sink))
                )
                .map(SearchResponse::getHits)
                .filter(hits -> hits.totalHits > 0)
                .map(hits  -> hits.getAt(0))
                .map(SearchHit::getSourceAsMap)
                .map(map -> objectMapper.convertValue(map, genericType));
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
    	final IndexRequest indexRequest = new IndexRequest(index, type, UUID.randomUUID().toString());
        final String json = objectMapper.writeValueAsString(doc);
        indexRequest.source(json, XContentType.JSON);
        client.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
    }

    public Mono<UpdateResponse> updateDoc(String docId, XContentBuilder json) {
        return Mono.create(sink -> {
            try {
                doUpdate(docId, json, listenerToSink(sink));
            } catch (IOException e) {
        	   sink.error(e);
			}
        });
    }

    private void doUpdate(String docId, XContentBuilder json, ActionListener<UpdateResponse> listener) throws IOException {
    	final UpdateRequest updateRequest = new UpdateRequest(index, type, docId);
        updateRequest.doc(json);
        updateRequest.retryOnConflict(5);
        client.updateAsync(updateRequest, RequestOptions.DEFAULT, listener);
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
}
