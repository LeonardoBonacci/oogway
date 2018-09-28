package guru.bonacci.oogway.sannyas.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Predicate;

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
import reactor.util.function.Tuple2;

@Service
public class SannyasService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private SannyasinPicker sannyasinPicker;

	@Autowired
	private ForePlayer forePlayer;

	@Autowired
	private CleaningAgent cleaningAgent;

	// TODO is this complexity really necessary?
	public Flux<GemCarrier> feed(String input) {
		logger.info("About to analyzer input: '" + input + "'");

		// we pick the sannyasin only at subscription
		Mono<Sannyasin> sannyas = Mono.defer(() -> sannyasinPicker.pickOne());
		
		// pre-process, and combine with mono-sannyas
		Mono<Tuple2<String, Sannyasin>> s = forePlayer.play(sannyas)
												 .map(fs -> fs.apply(input))
												 .zipWith(sannyas);

		// seek on sannyas
		Flux<GemCarrier> gg = s.flatMapMany(t -> t.getT2().seek(t.getT1()));

		// post-process, and combine with mono-sannyas
		Flux<Tuple2<GemCarrier, Predicate<GemCarrier>>> ff = gg.zipWith(cleaningAgent.clutterFilter(sannyas));
		return ff.filter(t -> t.getT2().test(t.getT1())).map(t -> t.getT1());
	}
}
