package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.oracle.client.OracleClientConfig;
import guru.bonacci.oogway.spectre.geo.client.GeoClientConfig;

@Configuration
@ComponentScan
@Import({OracleClientConfig.class, GeoClientConfig.class})
@PropertySource("classpath:web.properties")
public class WebConfig {
}
