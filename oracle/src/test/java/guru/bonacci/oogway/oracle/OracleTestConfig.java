
package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:/oracle-test.properties")
public class OracleTestConfig {

}
