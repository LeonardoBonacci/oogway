package guru.bonacci.oogway.sannyas;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.oracle.OracleTestConfig;

@Configuration
@ComponentScan
@Import(OracleTestConfig.class)
@PropertySource("classpath:/sannyas-test.properties")
public class SannyasTestConfig {

}
