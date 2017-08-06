package guru.bonacci.oogway.spectre.localtimer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels;

@Configuration
@ComponentScan
@EnableBinding(LocalTimerEventChannels.class)
@PropertySource("classpath:localtimer.properties")
public class LocalTimerConfig {
}