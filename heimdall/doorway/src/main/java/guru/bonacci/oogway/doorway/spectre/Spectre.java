package guru.bonacci.oogway.doorway.spectre;

import reactor.core.publisher.Mono;

public interface Spectre {

	Mono<Void> eavesdrop(String q, String ip);
}
