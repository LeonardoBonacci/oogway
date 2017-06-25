package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.oracle.api.OracleAPIConfig;

@Configuration
@ComponentScan
@Import(OracleAPIConfig.class)
public class WebConfig {

}
