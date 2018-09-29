package guru.bonacci.oogway.sannyas;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannyas.services.SannyasHandler;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching(proxyTargetClass=true)
public class SannyasServer {

	@Bean
	RouterFunction<ServerResponse> helloRouterFunction(SannyasHandler handler) {
		return route(GET("/feed"), handler::feed);
	}

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
