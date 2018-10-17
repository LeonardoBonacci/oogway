package guru.bonacci.oogway.sannyas;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannyas.services.SannyasService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching(proxyTargetClass=true)
@Slf4j
public class SannyasServer {

	@Bean
	RouterFunction<ServerResponse> routes(SannyasService serv) {
		return route(GET("/feed").and(accept(APPLICATION_STREAM_JSON)), 
						req -> { String q = req.queryParam("q").orElse("");
								 return ok().contentType(APPLICATION_STREAM_JSON)
										 	.body(serv.feed(q).doOnEach(s -> log.info(s.toString())), GemCarrier.class);
								});
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
