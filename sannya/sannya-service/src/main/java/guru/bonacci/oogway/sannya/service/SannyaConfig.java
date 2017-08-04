package guru.bonacci.oogway.sannya.service;

import java.util.Properties;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.oracle.client.OracleClientConfig;
import guru.bonacci.oogway.sannya.service.services.SannyaSink;

@Configuration
@ComponentScan
@Import(OracleClientConfig.class) 
@EnableBinding(SannyaSink.class)
@PropertySource("classpath:sannyas.properties")
public class SannyaConfig {

	@Bean
	public StanfordCoreNLP LemmatizatorPipeline() {
		// Create StanfordCoreNLP object properties, with POS tagging
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}
}
