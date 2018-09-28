package guru.bonacci.oogway.sannyas.processing;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * “Every aspect of your life is anchored energetically in your living space, so
 * clearing clutter can completely transform your entire existence.” 
 * - Karen Kingston
 */
@Component
public class CleaningAgent {

	@Autowired
	private ProfanityFilter profanityFilter;

	public Mono<Predicate<GemCarrier>> clutterFilter(Mono<Sannyasin> sannyas) {
		return Flux.concat(sannyas.flatMapMany(Sannyasin::postfilteringStep), Mono.just(profanityFilter))
				.reduce(p -> true, Predicate::and);
	}
}
