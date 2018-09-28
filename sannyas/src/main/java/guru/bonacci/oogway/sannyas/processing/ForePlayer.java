package guru.bonacci.oogway.sannyas.processing;

import static java.util.function.Function.identity;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * “Rationalization is foreplay with one's conscience.” 
 * ― Doug Cooper
 */
@Component
public class ForePlayer {

	@Autowired
	public DuplicateRemover duplicateRemover;

	public Mono<Function<String, String>> play(Mono<Sannyasin> sannyas) {
		return Flux.concat(sannyas.flatMapMany(Sannyasin::preprocessingSteps), Mono.just(duplicateRemover))
				.reduce(identity(), Function::andThen);
	}	
}

