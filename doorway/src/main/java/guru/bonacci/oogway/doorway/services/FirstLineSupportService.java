package guru.bonacci.oogway.doorway.services;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.doorway.oracle.Oracle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Service
public class FirstLineSupportService {

	private final Function<String, Oracle> oracleFactory;
	

	public Mono<GemCarrier> searchOne(String q, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.findOne(q)
					.doOnEach(gem -> log.info("oracle responded: " + gem));
	}
}
