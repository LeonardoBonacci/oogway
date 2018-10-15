package guru.bonacci.spectre.weather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import guru.bonacci.spectre.spectreshared.es.ElasticConfig;
import guru.bonacci.spectre.spectreshared.events.SpectreStreams;
import guru.bonacci.spectre.weather.services.WeatherService;

@SpringBootApplication
@EnableBinding({SpectreStreams.class, Processor.class})
@Import(ElasticConfig.class)
public class WeatherServer {
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherServer.class, args);
	}
	
	
	@Bean
	CommandLineRunner demo(WeatherService serv) {
		return args -> {
			serv.enrich("369c180c-c158-465c-80d7-85c09ae15a4a").subscribe(System.out::println);
		};
	}
}
