package guru.bonacci.oogway.sannya.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:sannya-client.properties")
public class SannyaClientConfig {

}
