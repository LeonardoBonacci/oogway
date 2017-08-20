package guru.bonacci.oogway.oracle.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@IntegrationComponentScan
@EnableBinding(OracleEventChannels.class)
@PropertySource("classpath:oracle.properties")
public class OracleServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "oracle-server");
		SpringApplication.run(OracleServer.class, args);
	}
}
