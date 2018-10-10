package guru.bonacci.oogway.doorway.clients;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());
	
	@Autowired
	private BiFunction<String, String, WebClient> webClientFactory;

	@Value("${service.oracle.url}") 
	private String url;

	
	public Mono<GemCarrier> search(String searchString, Credentials creds) {
		WebClient webClient = webClientFactory.apply(creds.getUsername(), creds.getPassword());

		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new OracleApi() {
		        @Override
		        public Mono<GemCarrier> search(String q) {
		        	logger.error(cause.getMessage());
		            return Mono.just(GemCarrier.builder().saying("Can't reach the Oracle").author("Sorry!").build());
		        }
		    })
			.target(OracleApi.class, url);
		
    	return oracle.search(searchString);
    }
}

