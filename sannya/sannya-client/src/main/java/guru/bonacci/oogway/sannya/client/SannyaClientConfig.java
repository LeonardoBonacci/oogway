package guru.bonacci.oogway.sannya.client;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@ComponentScan
@IntegrationComponentScan
@EnableBinding(SannyaSource.class)
@PropertySource("classpath:sannya-client.properties")
public class SannyaClientConfig {

}
