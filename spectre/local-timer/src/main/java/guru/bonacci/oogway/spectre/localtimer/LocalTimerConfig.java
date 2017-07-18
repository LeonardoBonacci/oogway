package guru.bonacci.oogway.spectre.localtimer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:localtimer.properties")
public class LocalTimerConfig {

}
