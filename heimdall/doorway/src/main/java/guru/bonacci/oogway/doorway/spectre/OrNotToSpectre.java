package guru.bonacci.oogway.doorway.spectre;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@ConditionalOnMissingBean(ToSpectre.class)
public class OrNotToSpectre implements Spectre {

	@Override
	public Mono<Void> eavesdrop(String q, String ip) {
		log.info("day off..");
		return Mono.empty();
	}
}
