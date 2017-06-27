package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@ComponentScan
@PropertySource("classpath:oracle.properties")
public class OracleConfig {

}
