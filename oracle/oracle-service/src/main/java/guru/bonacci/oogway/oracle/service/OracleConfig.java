package guru.bonacci.oogway.oracle.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;

@Configuration
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@ComponentScan
@IntegrationComponentScan
@EnableBinding(OracleEventChannels.class)
@PropertySource("classpath:oracle.properties")
public class OracleConfig {

}
