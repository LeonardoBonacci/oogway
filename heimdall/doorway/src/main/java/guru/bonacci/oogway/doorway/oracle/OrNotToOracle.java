package guru.bonacci.oogway.doorway.oracle;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnMissingBean(ToOracle.class)
public class OrNotToOracle implements Oracle {

	@Override
	public Mono<GemCarrier> enquire(String q, String apikey) {
		return Mono.just(new GemCarrier("I'm off today...", "oogway"));
	}
}
