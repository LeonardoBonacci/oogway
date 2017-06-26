package guru.bonacci.oogway.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.oracle.connect.OracleInterfaceConfig;

@Configuration
@ComponentScan
@Import(OracleInterfaceConfig.class)
public class WebConfig {

}
