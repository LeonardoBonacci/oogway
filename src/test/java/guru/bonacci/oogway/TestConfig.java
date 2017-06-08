package guru.bonacci.oogway;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.es.ESTestConfig;
import guru.bonacci.oogway.jms.JMSConfig;
import guru.bonacci.oogway.sannyas.SannyasConfig;

@Configuration
@Import(value = {ESTestConfig.class, SannyasConfig.class, JMSConfig.class})
public class TestConfig {
}
