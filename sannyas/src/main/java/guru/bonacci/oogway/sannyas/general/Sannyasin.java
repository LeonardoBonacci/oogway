package guru.bonacci.oogway.sannyas.general;

import static org.apache.commons.lang3.StringUtils.split;

import java.util.function.Function;
import java.util.function.Predicate;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Sannyasin: Seeker for Truth (is basically a Worker).
 * 
 * Sannyas means courage more than anything else, because it is a declaration of
 * your individuality, a declaration of freedom, a declaration that you will not
 * be any more part of the mob madness, the mob psychology. It is a declaration
 * that you are becoming universal; you will not belong to any country, to any
 * church, to any race, to any religion.”
 */
public interface Sannyasin {

	Flux<Function<String,String>> preprocessingSteps();

	default Flux<GemCarrier> seek(String tagsAsString) {
		return getScraper().flatMapMany(s -> s.find(split(tagsAsString)));
	}

	Mono<Scraper> getScraper();

	Flux<Predicate<GemCarrier>> postfilteringStep();
}
