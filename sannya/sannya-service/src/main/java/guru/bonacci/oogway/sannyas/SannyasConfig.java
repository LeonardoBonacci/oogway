package guru.bonacci.oogway.sannyas;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.oracle.client.OracleClientConfig;

@Configuration
@ComponentScan
@Import(OracleClientConfig.class)
@PropertySource("classpath:sannyas.properties")
public class SannyasConfig {

	@Bean
	public StanfordCoreNLP LemmatizatorPipeline() {
		// Create StanfordCoreNLP object properties, with POS tagging
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}
}
