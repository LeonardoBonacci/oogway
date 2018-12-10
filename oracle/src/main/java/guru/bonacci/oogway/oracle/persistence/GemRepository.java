package guru.bonacci.oogway.oracle.persistence;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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
    	this.adapter = adapter.theIndex(INDEX).theType(TYPE).theGenericType(Gem.class);
    }


	public Mono<Gem> findOne(String searchString) {
		log.info("looking for one " + searchString);
		return findOne(searchString, null);
	}

	public Mono<Gem> findOne(String searchString, String author) {
		return adapter.searchOne(createFindQuery(searchString, author));
	}

	private SearchSourceBuilder createFindQuery(String saying, String author) {
		SearchSourceBuilder queryBuilder = new SearchSourceBuilder().query(matchQuery(Gem.SAYING, saying));
		if (author != null)
			queryBuilder.postFilter(termQuery(Gem.AUTHOR, author));
		return queryBuilder;
	}
}
