package guru.bonacci.oogway.oracle.persistence;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;
import guru.bonacci.oogway.relastic.ElasticAdapter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class GemRepository {

	public static final String INDEX = "oracle";
	public static final String TYPE = "quote";
	
    private final ElasticAdapter<Gem> adapter;

	public GemRepository(ElasticAdapter<Gem> adapter) {
    	this.adapter = adapter.index(INDEX).type(TYPE).genericType(Gem.class);
    }


    public Mono<Void> insert(Gem gem) {
		log.info("about to insert " + gem);
		return index(gem).then();
	}

    private Mono<IndexResponse> index(Gem gem) {
        return adapter.indexDoc(gem)
                .doOnError(e -> log.error("Unable to index {}", gem, e));
    }

	public Mono<Gem> find(String searchString) {
		return find(searchString, null);
	}

	public Mono<Gem> find(String searchString, String author) {
		log.info("looking for " + searchString + " by " + author);
		return adapter.search(createFindQuery(searchString, author));
	}

	private SearchSourceBuilder createFindQuery(String saying, String author) {
		SearchSourceBuilder queryBuilder = new SearchSourceBuilder().query(matchQuery(Gem.SAYING, saying));
		if (author != null)
			queryBuilder.postFilter(termQuery(Gem.AUTHOR, author));
		return queryBuilder;
	}

	public Mono<Gem> random() {
		return adapter.random();
	}
}
