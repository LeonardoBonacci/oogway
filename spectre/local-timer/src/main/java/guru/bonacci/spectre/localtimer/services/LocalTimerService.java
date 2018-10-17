package guru.bonacci.spectre.localtimer.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.spectre.spectreshared.es.ElasticAdapter;
import guru.bonacci.spectre.spectreshared.es.Spec;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LocalTimerService implements SpectreService {

	public static final String serviceURL = "http://api.geonames.org/timezoneJSON";
	public static final String searchQuery = "?lat=#lat#&lng=#lng#&username=#username#";

	private final ElasticAdapter repo;

	private final WebClient client;

	private final String username;
	
	public LocalTimerService(ElasticAdapter es, @Value("${geo.name.username:leonardobonacci}") String username) {
		this.repo = es;
		this.username = username;
		
		client = WebClient.create(serviceURL);
	}


	public Mono<String> enrich(String id) {
		log.info("processing " + id);

		return repo.findById(id)
			.filter(spec -> spec.getGeoip().getLatitude() != null)
			.flatMap(this::webCall)
			.flatMap(data -> repo.update(id, "localtimer", data).then(Mono.fromSupplier(() -> data.toString())))
			.onErrorReturn("no weather");
	}
	
	@SuppressWarnings("rawtypes")
	public Mono<Map> webCall(Spec spec) {
		String q = searchQuery.replace("#lat#", String.valueOf(spec.getGeoip().getLatitude()))
							  .replace("#lng#", String.valueOf(spec.getGeoip().getLongitude()))
							  .replace("#username#", username);

		return client.get()
					.uri(q)
					.retrieve()
					.bodyToMono(Map.class)
					.doOnEach(s -> log.debug("" + s.get()));
	}

}
