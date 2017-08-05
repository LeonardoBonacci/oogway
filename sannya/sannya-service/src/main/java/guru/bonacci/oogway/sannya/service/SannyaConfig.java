package guru.bonacci.oogway.sannya.service;

import java.util.Properties;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannya.service.events.SannyaEventChannels;

@Configuration
@ComponentScan
@EnableBinding(SannyaEventChannels.class)
@IntegrationComponentScan
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
