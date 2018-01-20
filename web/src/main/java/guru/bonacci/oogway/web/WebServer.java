package guru.bonacci.oogway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.client.MyClient;
import guru.bonacci.oogway.oracle.client.OracleClientConfig;
import guru.bonacci.oogway.web.events.WebEventChannels;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableFeignClients(basePackageClasses = MyClient.class)
@EnableDiscoveryClient
@EnableBinding(WebEventChannels.class)
@IntegrationComponentScan
@Import(OracleClientConfig.class)
@PropertySource("classpath:web.properties")
public class WebServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}
}
