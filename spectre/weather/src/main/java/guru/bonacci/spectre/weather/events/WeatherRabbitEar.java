package guru.bonacci.spectre.weather.events;

import org.springframework.stereotype.Component;

import guru.bonacci.spectre.spectreshared.events.SpectreRabbitEar;
import guru.bonacci.spectre.weather.services.WeatherService;

@Component
public class WeatherRabbitEar extends SpectreRabbitEar {

	public WeatherRabbitEar(WeatherService service) {
		super(service);
	}
}
