package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.domain.GemCarrier;
import reactor.core.publisher.Mono;

public interface Oracle {


	Mono<GemCarrier> findOne(String q);
	
}
