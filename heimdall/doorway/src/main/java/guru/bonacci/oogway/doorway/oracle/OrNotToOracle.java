package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OrNotToOracle implements Oracle {

	
	@Override
	public Mono<GemCarrier> findOne(String q) {
		log.info("findOne on my day off");
		return Mono.error(new Exception("I will be caught"));
	}

	@Override
	public Mono<String> insert(GemCarrier gem) {
		log.info("insert on my day off");
		return Mono.error(new Exception("I will be caught"));
	}

	@Override
	public Mono<Void> update(GemIdCarrier gem) {
		log.info("update on my day off");
		return Mono.error(new Exception("I will be caught"));
	}

	@Override
	public Mono<Void> delete(String id) {
		log.info("delete on my day off");
		return Mono.error(new Exception("I will be caught"));
	}

	@Override
	public Mono<GemIdCarrier> findById(String id) {
		log.info("searchOne on my day off");
		return Mono.error(new Exception("I will be caught"));
	}

	@Override
	public Flux<GemCarrier> all() {
		log.info("all on my day off");
		return Flux.error(new Exception("I will be caught"));
	}

	@Override
	public Flux<GemCarrier> find(String searchString) {
		log.info("find on my day off");
		return Flux.error(new Exception("I will be caught"));
	}
}
