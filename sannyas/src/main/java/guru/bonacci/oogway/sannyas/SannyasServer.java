package guru.bonacci.oogway.sannyas;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannyas.events.Binding;


@EnableBinding(Binding.class)
@EnableCaching(proxyTargetClass=true)
@SpringBootApplication
public class SannyasServer {

	
	@Bean
	public StanfordCoreNLP lemmatizatorPipeline() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		return new StanfordCoreNLP(props);
	}

	public static void main(String[] args) {
		SpringApplication.run(SannyasServer.class, args);
	}
}
