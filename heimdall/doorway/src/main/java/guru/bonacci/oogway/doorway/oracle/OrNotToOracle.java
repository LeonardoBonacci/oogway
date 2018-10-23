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
		return Mono.fromSupplier(() -> GemCarrier.builder().saying("Nice try. I'm off today...").author("oogway").build());
	}

	@Override
	public Mono<String> insert(GemCarrier gem) {
		log.info("insert on my day off");
		return Mono.fromSupplier(() -> "outoforder");
	}

	@Override
	public Mono<Boolean> update(GemIdCarrier gem) {
		log.info("update on my day off");
		return Mono.fromSupplier(() -> Boolean.FALSE);
	}

	@Override
	public Mono<Boolean> delete(String id) {
		log.info("delete on my day off");
		return Mono.fromSupplier(() -> Boolean.FALSE);
	}

	@Override
	public Mono<GemIdCarrier> findById(String id) {
		log.info("searchOne on my day off");
		return Mono.fromSupplier(() -> new GemIdCarrier("Nice try. I'm off today..."));
	}

	@Override
	public Flux<GemCarrier> all() {
		log.info("all on my day off");
		return Flux.defer(() -> Flux.just(GemCarrier.builder().saying("Nice try. I'm off today...").author("oogway").build()));
	}

	@Override
	public Flux<GemCarrier> find(String searchString) {
		log.info("find on my day off");
		return Flux.defer(() -> Flux.just(GemCarrier.builder().saying("Nice try. I'm off today...").author("oogway").build()));
	}
}
