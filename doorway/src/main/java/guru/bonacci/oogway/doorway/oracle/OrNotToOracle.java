package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.domain.GemCarrier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class OrNotToOracle implements Oracle {

	
	@Override
	public Mono<GemCarrier> findOne(String q) {
		log.info("findOne on my day off");
		return Mono.error(new Exception("I will be caught"));
	}
}
