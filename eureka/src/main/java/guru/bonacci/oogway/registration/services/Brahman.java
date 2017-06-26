package guru.bonacci.oogway.registration.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka server for managing the micro-services
 */
@SpringBootApplication
@EnableEurekaServer
public class Brahman {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "brahman");
		SpringApplication.run(Brahman.class, args);
	}
}
