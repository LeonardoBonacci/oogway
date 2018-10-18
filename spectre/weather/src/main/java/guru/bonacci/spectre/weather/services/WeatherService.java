package guru.bonacci.spectre.weather.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.spectre.utilities.enrichment.SpectreService;
import guru.bonacci.spectre.utilities.es.Spec;
import guru.bonacci.spectre.utilities.es.SpectreRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherService implements SpectreService {

	public static final String serviceURL = "http://api.openweathermap.org/data/2.5/weather";
	public static final String searchQuery = "?lat=#lat#&lon=#lon#&appid=#apiKey#";

	private final WeatherCallAdmin weatherCallAdmin;

	private final SpectreRepository repo;

	private final WebClient client;
	
	private final String apiKey;

	public WeatherService(SpectreRepository es, WeatherCallAdmin admin, @Value("${openweathermap.apikey}") String key) {
		this.repo = es;
		this.weatherCallAdmin = admin;
		this.apiKey = key;
		
		client = WebClient.create(serviceURL);
	}

	
	public Mono<String> enrich(final String id) {
		log.info("processing " + id);

		return weatherCallAdmin.isWeatherCallAllowed(id)
						.filter(b -> b)
						.flatMap(b -> repo.findById(id))
						.filter(spec -> spec.getGeoip().getLatitude() != null)
						.flatMap(this::call)
						.flatMap(data -> repo.update(id, "weather", data).then(Mono.fromSupplier(() -> data.toString())))
						.onErrorReturn("no weather");
	}

	@SuppressWarnings("rawtypes")
	public Mono<Map> call(Spec spec) {
		String q = searchQuery.replace("#lat#", String.valueOf(spec.getGeoip().getLatitude()))
							  .replace("#lon#", String.valueOf(spec.getGeoip().getLongitude()))
							  .replace("#apiKey#", apiKey);

		return client.get()
					.uri(q)
					.retrieve()
					.bodyToMono(Map.class)
					.doOnEach(s -> log.debug("" + s.get()));
	}
}
