package guru.bonacci.oogway.web;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.client.OracleClientConfig;
import guru.bonacci.oogway.web.events.WebEventChannels;

@Configuration
@ComponentScan
@EnableBinding(WebEventChannels.class)
@IntegrationComponentScan
@Import(OracleClientConfig.class)
@PropertySource("classpath:web.properties")
public class WebConfig {
}
