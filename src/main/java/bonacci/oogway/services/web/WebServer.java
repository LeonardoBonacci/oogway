package bonacci.oogway.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import bonacci.oogway.es.ESConfig;
import bonacci.oogway.jms.MessagingConfiguration;
import bonacci.oogway.sannyas.SannyasConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import({MessagingConfiguration.class, ESConfig.class, SannyasConfiguration.class})
public class WebServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}
}
