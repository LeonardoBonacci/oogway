package guru.bonacci.oogway.doorway.lumber;

import reactor.core.publisher.Mono;

public interface Lumberjack {

	Mono<Boolean> isGreedy(String apikey);
}
