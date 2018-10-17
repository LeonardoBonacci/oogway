package guru.bonacci.oogway.oracle.persistence;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@RequiredArgsConstructor
public class GemRepository {

    private final ElasticAdapter adapter;


    public Mono<Void> insert(Gem gem) {
		log.info("about to insert " + gem);
		return adapter.index(gem).then();
	}

	public Mono<Gem> find(String searchString) {
		return find(searchString, null);
	}

	public Mono<Gem> find(String searchString, String author) {
		return adapter.find(searchString, author);
	}

	public Mono<Gem> random() {
		return adapter.random();
	}
}
