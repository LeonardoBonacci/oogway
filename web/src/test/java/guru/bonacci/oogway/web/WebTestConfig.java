package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.oracle.client.OracleClientTestConfig;

@Configuration
@ComponentScan
@Import(OracleClientTestConfig.class)
public class WebTestConfig {

}
