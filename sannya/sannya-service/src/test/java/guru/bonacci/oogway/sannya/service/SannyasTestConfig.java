package guru.bonacci.oogway.sannya.service;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@Configuration
@ComponentScan
public class SannyasTestConfig {

	@Bean
	public StanfordCoreNLP LemmatizatorPipeline() {
		// Create StanfordCoreNLP object properties, with POS tagging
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}
}
