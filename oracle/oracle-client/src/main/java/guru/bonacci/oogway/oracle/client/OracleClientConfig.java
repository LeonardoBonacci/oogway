package guru.bonacci.oogway.oracle.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class OracleClientConfig {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
