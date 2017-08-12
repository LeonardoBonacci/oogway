package guru.bonacci.oogway.spectre.localtimer.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.spectre.secretpersistence.Spec;
import guru.bonacci.oogway.spectre.secretpersistence.SpecRepository;

@Service
public class LocalTimerService {

	private final Logger logger = getLogger(this.getClass());

	public static final String serviceURL = "http://api.geonames.org/timezoneJSON";
	public static final String searchQuery = "?lat=#lat#&lng=#lng#&username=#username#";

	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpecRepository repo;

	@Value("${geo.name.username:leonardobonacci}")
	private String username;
	
	public void enrich(String id) {
		Spec spec = repo.findOne(id);

		try {
			String q = searchQuery.replace("#lat#", String.valueOf(spec.geoip.latitude))
								  .replace("#lng#", String.valueOf(spec.geoip.longitude))
								  .replace("#username#", username);

			// ...and gross exception handling
			@SuppressWarnings("unchecked")
			Map<String,Object> enrichmentData = restTemplate.getForObject(serviceURL + q, Map.class);
			logger.debug(enrichmentData.toString());

			repo.addData("localtimer", enrichmentData, spec);
		} catch(Exception e) {
			logger.error("Oops", e);
		}
	}
}
