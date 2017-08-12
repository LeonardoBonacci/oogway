package guru.bonacci.oogway.spectre.sentiment;

import java.util.Properties;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.spectre.secretpersistence.SecretPersistenceConfig;
import guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels;

@Configuration
@ComponentScan
@EnableBinding(SentimentEventChannels.class)
@Import(SecretPersistenceConfig.class)
@PropertySource("classpath:sentiment.properties")
public class SentimentConfig {
	
	@Bean
	public StanfordCoreNLP sentimentPipeline() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		return new StanfordCoreNLP(props);
	}
}
