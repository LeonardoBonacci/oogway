package guru.bonacci.oogway.oracle.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:oracle-client.properties")
public class OracleClientConfig {
}	
