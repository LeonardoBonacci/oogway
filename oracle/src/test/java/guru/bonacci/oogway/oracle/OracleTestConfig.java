
package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy 
@PropertySource("classpath:/oracle-test.properties")
public class OracleTestConfig {
	
}
