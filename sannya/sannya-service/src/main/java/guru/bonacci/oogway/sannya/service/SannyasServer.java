package guru.bonacci.oogway.sannya.service;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannya.service.events.SannyaEventChannels;

/**
 * Micro-service for retrieval and indexing of wisdom
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCaching(proxyTargetClass=true)
@EnableBinding(SannyaEventChannels.class)
@IntegrationComponentScan
public class SannyasServer {

	public static void main(String[] args) {
		SpringApplication.run(SannyasServer.class, args);
	}
	
	@Bean
	public StanfordCoreNLP lemmatizatorPipeline() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}
}
