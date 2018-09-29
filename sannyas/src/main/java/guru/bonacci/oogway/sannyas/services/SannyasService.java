package guru.bonacci.oogway.sannyas.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.processing.CleaningAgent;
import guru.bonacci.oogway.sannyas.processing.ForePlayer;
import guru.bonacci.oogway.sannyas.processing.SannyasinPicker;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SannyasService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private SannyasinPicker sannyasinPicker;

	@Autowired
	private ForePlayer forePlayer;

	@Autowired
	private CleaningAgent cleaningAgent;


	public Flux<GemCarrier> feed(String input) {
		logger.info("About to analyzer input: '" + input + "'");

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
