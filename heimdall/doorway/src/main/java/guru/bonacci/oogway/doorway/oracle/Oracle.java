package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Oracle {


	Mono<GemCarrier> findOne(String q);
	
    Mono<String> insert(GemCarrier gem);

    Mono<Boolean> update(GemIdCarrier gem);
    
    Mono<Boolean> delete(String id);

	Mono<GemIdCarrier> findById(String id);

	Flux<GemCarrier> all();

	Flux<GemCarrier> find(String q);
}
