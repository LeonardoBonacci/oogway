package guru.bonacci.oogway.doorway.lumber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
@ConditionalOnMissingBean(ToLumber.class)
public class OrNotToLumber implements Lumberjack {

	@Override
	public Mono<Void> lumber(String apikey) {
		//doing nothing
		return Mono.empty();
	}
}