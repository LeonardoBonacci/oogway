package guru.bonacci.oogway.spectre.geo.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.spectre.geo.service.event.GeoEventChannels;

@Configuration
@ComponentScan
@IntegrationComponentScan
@EnableBinding(GeoEventChannels.class)
@PropertySource("classpath:geo.properties")
public class GeoConfig {

}
