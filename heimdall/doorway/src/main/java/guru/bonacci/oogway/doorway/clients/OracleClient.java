package guru.bonacci.oogway.doorway.clients;

import java.net.URLEncoder;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class OracleClient {

	private final BiFunction<String, String, WebClient> webClientFactory;

	@Value("${service.oracle.url}") 
	private String url;

	
	@SuppressWarnings("deprecation")
	public Mono<GemCarrier> search(String searchString, Credentials creds) {
		WebClient webClient = webClientFactory.apply(creds.getUsername(), creds.getPassword());

		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new OracleApi() {
		        @Override
		        public Mono<GemCarrier> search(String q) {
		        	log.error(cause.getMessage());
		            return Mono.just(GemCarrier.builder().saying("Can't reach the Oracle").author("Sorry!").build());
		        }
		    })
			.target(OracleApi.class, url);
		
    	return oracle.search(URLEncoder.encode(searchString));
    }
}

