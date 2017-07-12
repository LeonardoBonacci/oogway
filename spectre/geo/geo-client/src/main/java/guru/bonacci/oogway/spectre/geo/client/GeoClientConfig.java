package guru.bonacci.oogway.spectre.geo.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:geo-client.properties")
public class GeoClientConfig {

}
