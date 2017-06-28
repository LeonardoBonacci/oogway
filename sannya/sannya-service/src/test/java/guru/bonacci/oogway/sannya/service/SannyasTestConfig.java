package guru.bonacci.oogway.sannya.service;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.oracle.client.OracleClientTestConfig;

@Configuration
@ComponentScan
@Import(OracleClientTestConfig.class)
public class SannyasTestConfig {

	@Bean
	public StanfordCoreNLP LemmatizatorPipeline() {
		// Create StanfordCoreNLP object properties, with POS tagging
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}
}
