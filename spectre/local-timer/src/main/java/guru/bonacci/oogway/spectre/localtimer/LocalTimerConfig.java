package guru.bonacci.oogway.spectre.localtimer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels;
import guru.bonacci.oogway.spectre.secretpersistence.SecretPersistenceConfig;

@Configuration
@ComponentScan
@EnableBinding(LocalTimerEventChannels.class)
@Import(SecretPersistenceConfig.class)
@PropertySource("classpath:local-timer.properties")
public class LocalTimerConfig {
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
