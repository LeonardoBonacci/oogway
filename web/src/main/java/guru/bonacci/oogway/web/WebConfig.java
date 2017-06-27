package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.oracle.connect.OracleInterfaceConfig;

@Configuration
@ComponentScan
@Import(OracleInterfaceConfig.class)
@PropertySource("classpath:web.properties")
public class WebConfig {

}
