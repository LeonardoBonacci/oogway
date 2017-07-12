package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.spectre.geo.service.GeoConfig;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(GeoConfig.class)
public class GeoServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "geo-server");
		SpringApplication.run(GeoServer.class, args);
	}
}
