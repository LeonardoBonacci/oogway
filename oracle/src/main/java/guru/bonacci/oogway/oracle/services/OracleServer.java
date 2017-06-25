package guru.bonacci.oogway.oracle.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.oracle.OracleConfig;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(OracleConfig.class)
public class OracleServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "oracle-server");
		SpringApplication.run(OracleServer.class, args);
	}
}
