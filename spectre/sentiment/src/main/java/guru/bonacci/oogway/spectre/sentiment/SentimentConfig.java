package guru.bonacci.oogway.spectre.sentiment;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import guru.bonacci.oogway.spectre.sentiment.services.SentimentSink;

@Configuration
@ComponentScan
@EnableBinding(SentimentSink.class)
@PropertySource("classpath:sentiment.properties")
public class SentimentConfig {
}
