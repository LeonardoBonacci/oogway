package guru.bonacci.oogway.oracle.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import guru.bonacci.oogway.oracle.service.services.OracleSink;
import guru.bonacci.oogway.sannya.client.SannyaClientConfig;

@Configuration
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@ComponentScan
@PropertySource("classpath:oracle.properties")
@EnableBinding(OracleSink.class)
@Import(SannyaClientConfig.class)
public class OracleConfig {

}
