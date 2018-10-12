package guru.bonacci.spectre.sentiment;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding(SpectreStreams.class)
public class SentimentServer {

	public static void main(String[] args) {
		SpringApplication.run(SentimentServer.class, args);
	}

	@Bean
	public StanfordCoreNLP sentimentPipeline() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		return new StanfordCoreNLP(props);
	}
}
