package guru.bonacci.oogway.spectre.localtimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels;
import guru.bonacci.oogway.spectre.secretpersistence.SecretPersistenceConfig;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(LocalTimerEventChannels.class)
@Import(SecretPersistenceConfig.class)
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
