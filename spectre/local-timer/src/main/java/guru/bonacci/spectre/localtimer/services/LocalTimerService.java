package guru.bonacci.spectre.localtimer.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import reactor.core.publisher.Mono;

@Service
public class LocalTimerService implements SpectreService {

	private final Logger logger = getLogger(this.getClass());

	public static final String serviceURL = "http://api.geonames.org/timezoneJSON";
	public static final String searchQuery = "?lat=#lat#&lng=#lng#&username=#username#";

//	@Autowired
//	private SpecRepository repo;

	@Value("${geo.name.username:leonardobonacci}")
	private String username;
	
	public Mono<String> enrich(String id) {
		try {
			logger.error("processing " + id);
			// Too lazy for refined error handling today...
//			Spec spec = repo.findById(id).get();
//			String q = searchQuery.replace("#lat#", String.valueOf(spec.geoip.latitude))
//								  .replace("#lng#", String.valueOf(spec.geoip.longitude))
//								  .replace("#username#", username);
//
//			@SuppressWarnings("unchecked")
//			Map<String,Object> enrichmentData = restTemplate.getForObject(serviceURL + q, Map.class);
//			logger.debug(enrichmentData.toString());

//			repo.addData("localtimer", enrichmentData, spec);
			return Mono.just("TODO");
		} catch(Exception e) {
			logger.error("Oops", e);
			return Mono.just("no localtimer");
		}
	}
}
