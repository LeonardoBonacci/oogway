package guru.bonacci.oogway.spectre.sentiment;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.spectre.secretpersistence.SecretPersistenceConfig;
import guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(SentimentEventChannels.class)
@Import(SecretPersistenceConfig.class)
@PropertySource("classpath:sentiment.properties")
public class SentimentServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sentiment-server");
		SpringApplication.run(SentimentServer.class, args);
	}

	@Bean
	public StanfordCoreNLP sentimentPipeline() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		return new StanfordCoreNLP(props);
	}
}
