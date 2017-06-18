package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.oracle.OracleTestConfig;

@Configuration
@ComponentScan
@Import(OracleTestConfig.class)
@PropertySource("classpath:/web-test.properties")
public class WebTestConfig {

}