package guru.bonacci.oogway.spectre.sentiment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:sentiment.properties")
public class SentimentConfig {
}
