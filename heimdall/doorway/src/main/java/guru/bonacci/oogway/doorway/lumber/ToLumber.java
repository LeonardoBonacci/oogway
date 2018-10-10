package guru.bonacci.oogway.doorway.lumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.clients.LumberClient;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnProperty(name = "service.lumberjack.enabled", havingValue = "true")
public class ToLumber implements Lumberjack {

	private static final Long GREED_STARTS_HERE = 10l;
	
	@Autowired
	private LumberClient lumberClient;

	@Override
	public Mono<Boolean> greed(String apikey) {
		return lumberClient.visits(apikey)
							.map(v -> v > GREED_STARTS_HERE);
	}
}