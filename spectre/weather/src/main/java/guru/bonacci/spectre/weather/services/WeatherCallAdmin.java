package guru.bonacci.spectre.weather.services;

import org.springframework.stereotype.Component;

import guru.bonacci.spectre.utilities.collections.CustomPassiveExpiringCollection;
import reactor.core.publisher.Mono;

@Component
public class WeatherCallAdmin {

	// 60000 milliseconds in a minute
	private final CustomPassiveExpiringCollection<String> hitsAdministrator = new CustomPassiveExpiringCollection<>(60000l);

	public Mono<Boolean> isWeatherCallAllowed(String id) {
		// Maximum free usage of openweathermap is 60 calls per minute
		return Mono.fromRunnable(() -> hitsAdministrator.add(id))
				.then(Mono.fromSupplier(() -> hitsAdministrator.size() < 60));
	}
}
