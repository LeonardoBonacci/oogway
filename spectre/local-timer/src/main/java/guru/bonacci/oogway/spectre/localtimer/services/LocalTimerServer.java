package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.spectre.localtimer.LocalTimerConfig;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(LocalTimerConfig.class)
public class LocalTimerServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "localtimer-server");
		SpringApplication.run(LocalTimerServer.class, args);
	}
}
