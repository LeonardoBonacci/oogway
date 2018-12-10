package guru.bonacci.oogway.doorway.lumber;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class Lumberjack {


	public Mono<Boolean> isGreedy(String apikey) {
		return Mono.fromSupplier(() -> false);
	}
}