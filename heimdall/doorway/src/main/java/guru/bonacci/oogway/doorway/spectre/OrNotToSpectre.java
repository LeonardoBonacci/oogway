package guru.bonacci.oogway.doorway.spectre;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
@ConditionalOnMissingBean(ToSpectre.class)
public class OrNotToSpectre implements Spectre {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public Mono<Void> eavesdrop(String q, String ip) {
		logger.info("day off..");
		return Mono.empty();
	}
}
