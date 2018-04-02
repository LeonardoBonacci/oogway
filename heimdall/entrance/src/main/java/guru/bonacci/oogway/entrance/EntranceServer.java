package guru.bonacci.oogway.entrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.entrance.events.EntranceEventChannels;

/**
 * Service for the user to communicate with
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(EntranceEventChannels.class)
@IntegrationComponentScan
public class EntranceServer {

	public static void main(String[] args) {
		SpringApplication.run(EntranceServer.class, args);
	}
}
