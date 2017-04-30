package bonacci.oogway.services.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import bonacci.oogway.es.ESConfig;
import bonacci.oogway.jms.MessagingConfiguration;
import bonacci.oogway.sannyas.SannyasConfiguration;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import({MessagingConfiguration.class, SannyasConfiguration.class, ESConfig.class})
public class AccountsServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(AccountsServer.class, args);
	}
}
