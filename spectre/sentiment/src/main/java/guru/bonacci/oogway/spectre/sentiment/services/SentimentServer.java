package guru.bonacci.oogway.spectre.sentiment.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.spectre.sentiment.SentimentConfig;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(SentimentConfig.class)
public class SentimentServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sentiment-server");
		SpringApplication.run(SentimentServer.class, args);
	}
}
