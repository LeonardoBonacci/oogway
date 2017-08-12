package guru.bonacci.oogway.spectre.sentiment;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@Configuration
@ComponentScan
public class SentimentTestConfig {

	@Bean
	public StanfordCoreNLP sentimentPipeline() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		return new StanfordCoreNLP(props);
	}
}
