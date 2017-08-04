package guru.bonacci.oogway.spectre.geo.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.spectre.geo.service.services.SpectreSink;

@Configuration
@ComponentScan
@EnableBinding(SpectreSink.class)
@PropertySource("classpath:geo.properties")
public class GeoConfig {

}
