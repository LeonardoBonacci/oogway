package guru.bonacci.oogway.oracle.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { OracleClientConfig.class }))
public class OracleClientTestConfig {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
