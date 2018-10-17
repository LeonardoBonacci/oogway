package guru.bonacci.oogway.sannyas.services;

import org.springframework.stereotype.Service;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.processing.CleaningAgent;
import guru.bonacci.oogway.sannyas.processing.ForePlayer;
import guru.bonacci.oogway.sannyas.processing.SannyasinPicker;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SannyasService {

	private final SannyasinPicker sannyasinPicker;

	private final ForePlayer forePlayer;

	private final CleaningAgent cleaningAgent;


	public Flux<GemCarrier> feed(String input) {
		log.info("About to analyzer input: '" + input + "'");

		// we pick the sannyasin only at subscription, and cache for subsequent other subscriptions
		Mono<Sannyasin> sannyas = Mono.defer(() -> sannyasinPicker.pickOne()).cache();
		
		// pre-process
		Mono<String> prep = forePlayer.play(sannyas).map(fs -> fs.apply(input));

		// seek 
		Flux<GemCarrier> found = sannyas.flatMapMany(s -> prep.flatMapMany(p -> s.seek(p)));

		// and post-process
		return cleaningAgent.clutterFilter(sannyas)
							.flatMapMany(postp -> found.filter(f -> postp.test(f)));
	}
}
