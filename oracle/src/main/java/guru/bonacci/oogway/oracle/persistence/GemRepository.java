package guru.bonacci.oogway.oracle.persistence;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import guru.bonacci.oogway.relastic.ElasticAdapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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


    public Mono<String> insert(Gem gem) {
		log.info("about to insert " + gem);
		return index(gem).map(resp -> resp.getId());
	}

    private Mono<IndexResponse> index(Gem gem) {
        return adapter.indexDoc(gem)
                .doOnError(e -> log.error("Unable to index {}", gem, e));
    }

    public Mono<UpdateResponse> update(Gem gem) {
	    return adapter.updateDoc(gem)
  			  		  .doOnError(e -> log.error("Unable to update {}", gem, e));
    }

    public Mono<DeleteResponse> delete(String id) {
    	return adapter.deleteDoc(id)
  			  		  .doOnError(e -> log.error("Unable to delete {}", id, e));
    }

	public Mono<Gem> findById(String id) {
		log.info("looking for gem with id " + id);
		return adapter.findById(id);
	}

	public Mono<Gem> findOne(String searchString) {
		log.info("looking for one " + searchString);
		return findOne(searchString, null);
	}

	public Mono<Gem> findOne(String searchString, String author) {
		return adapter.searchOne(createFindQuery(searchString, author));
	}

	public Flux<Gem> all() {
		log.info("wanting all");
		return adapter.search(new SearchSourceBuilder().query(matchAllQuery()).size(10000));
	}

	public Flux<Gem> find(String searchString) {
		log.info("looking for " + searchString);
		return find(searchString, null);
	}

	public Flux<Gem> find(String searchString, String author) {
		return adapter.search(createFindQuery(searchString, author).size(10000));
	}

	public Mono<Gem> random() {
		return adapter.random();
	}
	

	private SearchSourceBuilder createFindQuery(String saying, String author) {
		SearchSourceBuilder queryBuilder = new SearchSourceBuilder().query(matchQuery(Gem.SAYING, saying));
		if (author != null)
			queryBuilder.postFilter(termQuery(Gem.AUTHOR, author));
		return queryBuilder;
	}
}
