package guru.bonacci.oogway.spectre.geo.client;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@ComponentScan
@EnableBinding(SpectreSource.class)
@IntegrationComponentScan
@PropertySource("classpath:geo-client.properties")
public class GeoClientConfig {

}
