package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.sannya.client.SannyaClientTestConfig;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy 
@Import(SannyaClientTestConfig.class)
public class OracleTestConfig {
	
}
