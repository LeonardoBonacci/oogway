package guru.bonacci.oogway.oracle.persistence;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.UUID;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@SuppressWarnings("deprecation")
@Component
@Slf4j
@RequiredArgsConstructor
public class ElasticAdapter {

	public static final String INDEX = "oracle";
	public static final String TYPE = "quote";
	
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    Mono<Gem> find(String searchString, String author) {
    	return search(createQuery(searchString, author));
    }

	private SearchSourceBuilder createQuery(String saying, String author) {
		SearchSourceBuilder queryBuilder = new SearchSourceBuilder().query(matchQuery(Gem.SAYING, saying));
		if (author != null)
			queryBuilder.postFilter(termQuery(Gem.AUTHOR, author));
		return queryBuilder;
	}

    Mono<Gem> search() {
    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
    	searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    	return search(searchSourceBuilder);
    }

    Mono<Gem> random() {
    	FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(QueryBuilders.matchAllQuery(), ScoreFunctionBuilders.randomFunction());
    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
    	searchSourceBuilder.query(functionScoreQueryBuilder);
    	return search(searchSourceBuilder);
    }

	private Mono<Gem> search(SearchSourceBuilder search) {
        return Mono
                .<SearchResponse>create(sink ->
                        client.searchAsync(new SearchRequest(INDEX).types(TYPE).source(search), listenerToSink(sink))
                )
                .map(SearchResponse::getHits)
                .filter(hits -> hits.totalHits > 0)
                .map(hits  -> hits.getAt(0))
                .map(SearchHit::getSourceAsMap)
                .map(map -> objectMapper.convertValue(map, Gem.class));
    }

    Mono<IndexResponse> index(Gem gem) {
        return indexDoc(gem)
                .doOnError(e -> log.error("Unable to index {}", gem, e));
    }

    private Mono<IndexResponse> indexDoc(Gem doc) {
        return Mono.create(sink -> {
            try {
                doIndex(doc, listenerToSink(sink));
            } catch (JsonProcessingException e) {
                sink.error(e);
            }
        });
    }

    private void doIndex(Gem doc, ActionListener<IndexResponse> listener) throws JsonProcessingException {
    	if (doc.getId() == null)
    		doc.setId(UUID.randomUUID().toString());

    	final IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, doc.getId());
        final String json = objectMapper.writeValueAsString(doc);
        indexRequest.source(json, XContentType.JSON);
        client.indexAsync(indexRequest, listener);
    }

    private <T> ActionListener<T> listenerToSink(MonoSink<T> sink) {
        return new ActionListener<T>() {
            @Override
            public void onResponse(T response) {
                sink.success(response);
            }

            @Override
            public void onFailure(Exception e) {
            	log.error(e.getMessage());
                sink.error(e);
            }
        };
    }

}
