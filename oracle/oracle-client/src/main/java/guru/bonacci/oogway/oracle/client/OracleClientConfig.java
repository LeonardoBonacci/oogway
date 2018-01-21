package guru.bonacci.oogway.oracle.client;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@EnableFeignClients(basePackageClasses = OracleClient.class)
@EnableCircuitBreaker
@PropertySource("classpath:oracle-client.properties")
public class OracleClientConfig {
}	
