package guru.bonacci.spectre.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.events.SpectreEventChannels;
import guru.bonacci.spectre.spectreshared.persistence.PersistenceConfig;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(SpectreEventChannels.class)
@Import(PersistenceConfig.class)
@PropertySource("classpath:weather.properties")
public class WeatherServer {
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "weather-server");
		SpringApplication.run(WeatherServer.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
