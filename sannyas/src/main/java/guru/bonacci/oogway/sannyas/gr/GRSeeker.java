package guru.bonacci.oogway.sannyas.gr;


import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.sannyas.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.general.Scraper;
import guru.bonacci.oogway.sannyas.steps.CharacterGuardian;
import guru.bonacci.oogway.sannyas.steps.KeyPhraser;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GRSeeker implements Sannyasin {

	private final CharacterGuardian characterGuardian;

	private final KeyPhraser keyPhraser;

	private final LengthFilter lengthFilter;

	private final GRScraper scraper;

	@Override
	public Flux<Function<String,String>> preprocessingSteps() {
		return Flux.just(characterGuardian, keyPhraser);
	}

	@Override
	public Flux<Predicate<GemCarrier>> postfilteringStep() {
		return Flux.just(lengthFilter);
	}

	@Override
	public Mono<Scraper> getScraper() {
		return Mono.just(scraper);
	};
}
