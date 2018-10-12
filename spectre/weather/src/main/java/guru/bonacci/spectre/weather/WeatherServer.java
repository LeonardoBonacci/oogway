package guru.bonacci.spectre.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding(SpectreStreams.class)
public class WeatherServer {
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherServer.class, args);
	}
}
