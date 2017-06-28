package guru.bonacci.oogway.sannya.service.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.sannya.service.SannyasConfig;

/**
 * Micro-service for retrieval and indexing of wisdom
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCaching(proxyTargetClass=true)
@Import(SannyasConfig.class)
public class SannyasServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sannyas-server");
		SpringApplication.run(SannyasServer.class, args);
	}
}
