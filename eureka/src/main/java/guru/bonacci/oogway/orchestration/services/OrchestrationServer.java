package guru.bonacci.oogway.orchestration.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka server for managing the micro-services
 */
@SpringBootApplication
@EnableEurekaServer
public class OrchestrationServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "orchestration");
		SpringApplication.run(OrchestrationServer.class, args);
	}
}
