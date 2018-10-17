package guru.bonacci.oogway.doorway.lumber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.clients.LumberClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.lumberjack.enabled", havingValue = "true")
public class ToLumber implements Lumberjack {

	private static final Long GREED_STARTS_HERE = 10l;
	
	private final LumberClient lumberClient;

	@Override
	public Mono<Boolean> isGreedy(String apikey) {
		return lumberClient.visits(apikey)
							.map(v -> v > GREED_STARTS_HERE);
	}
}