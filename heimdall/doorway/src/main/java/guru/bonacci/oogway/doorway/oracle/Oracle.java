package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

public interface Oracle {

	Mono<GemCarrier> enquire(String q, String apikey);
}
