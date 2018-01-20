package guru.bonacci.spectre.localtimer;

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
@PropertySource("classpath:local-timer.properties")
public class LocalTimerServer {
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "localtimer-server");
		SpringApplication.run(LocalTimerServer.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}