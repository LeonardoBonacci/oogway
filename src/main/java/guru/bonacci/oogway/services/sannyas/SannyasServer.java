package guru.bonacci.oogway.services.sannyas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.es.ESConfig;
import guru.bonacci.oogway.jms.JMSConfig;
import guru.bonacci.oogway.sannyas.SannyasConfiguration;

/**
 * Micro-service for retrieval and indexing of wisdom
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCaching(proxyTargetClass=true)
@Import({JMSConfig.class, SannyasConfiguration.class, ESConfig.class})
public class SannyasServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sannyas-server");
		SpringApplication.run(SannyasServer.class, args);
	}
}
