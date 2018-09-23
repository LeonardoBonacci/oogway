package guru.bonacci.oogway.doorway.oracle;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
@ConditionalOnMissingBean(ToOracle.class)
public class OrNotToOracle implements Oracle {

	@Override
	public GemCarrier enquire(String q, String apiKey) {
		return new GemCarrier("I'm off today...", "oogway");
	}
}
