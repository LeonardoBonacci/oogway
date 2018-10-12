package guru.bonacci.spectre.money.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import reactor.core.publisher.Mono;

@Service
public class MoneyService implements SpectreService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private MoneyCache cache;

//	@Autowired
//	private SpecRepository repo;

	public Mono<String> enrich(String id) {
		try {
			logger.error("processing " + id);
			// Too lazy for refined error handling today...
//			Spec spec = repo.findById(id).get();

			// Show me the money!!
//			String enrichmentData = cache.get(spec.geoip.country_code2);
//			logger.debug(enrichmentData);
			
//			repo.addData("income", enrichmentData, spec);
			return Mono.just("TODO");
		} catch(Exception e) {
			logger.error("Oops", e);
			return Mono.just("no money");
		}
	}
}
