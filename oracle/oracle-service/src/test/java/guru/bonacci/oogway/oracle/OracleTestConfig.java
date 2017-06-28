
package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.sannya.client.SannyaClientConfig;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy 
//TODO@Import(SannyaClientTestConfig.class)
@Import(SannyaClientConfig.class)
public class OracleTestConfig {
	
}
