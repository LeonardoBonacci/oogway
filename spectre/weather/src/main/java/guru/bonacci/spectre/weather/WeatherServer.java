package guru.bonacci.spectre.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Import;

import guru.bonacci.spectre.spectreshared.es.ElasticConfig;
import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding({SpectreStreams.class, Processor.class})
@Import(ElasticConfig.class)
public class WeatherServer {
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherServer.class, args);
	}
}
