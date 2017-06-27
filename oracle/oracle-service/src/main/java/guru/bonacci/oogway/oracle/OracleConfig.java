package guru.bonacci.oogway.oracle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import guru.bonacci.oogway.sannya.client.SannyaClientConfig;

@Configuration
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@ComponentScan
@PropertySource("classpath:oracle.properties")
@Import(SannyaClientConfig.class)
public class OracleConfig {

}
