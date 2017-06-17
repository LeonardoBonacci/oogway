
package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan
@TestPropertySource("classpath:/application-test.properties")
public class OracleTestConfig {

}
