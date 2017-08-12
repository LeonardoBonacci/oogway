package guru.bonacci.oogway.spectre.localtimer.events;

import static guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels.ENRICHMENT;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.secretpersistence.Spec;
import guru.bonacci.oogway.spectre.secretpersistence.SpecRepository;

@Component
public class LocalTimerRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpecRepository repo;

	@Value("${geo.name.username:leonardobonacci}")
	private String username;
	
	@StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		logger.info("Incoming... " + event.getContent());

		// lazy today, so no separate service class...
		Spec spec = repo.findOne(event.getContent());

		// ...and local variables here...
		String host = "http://api.geonames.org";
		String path = "/timezoneJSON";
		String searchQuery = "?lat=" + spec.getGeoip().latitude + "&lng=" + spec.getGeoip().longitude + "&username=" + username;

		// ...and gross exception handling
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> enrichmentData = restTemplate.getForObject(host + path + searchQuery, Map.class);
			repo.addData("localtimer", enrichmentData, spec);
		} catch(Exception e) {
			logger.info("Oops", e);
		}
	}
}
