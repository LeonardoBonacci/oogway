package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.oracle.client.OracleClientConfig;

@Configuration
@ComponentScan
@Import(OracleClientConfig.class) //TODO make  testconfig
@PropertySource("classpath:web.properties")
public class WebConfig {
}
