package guru.bonacci.oogway.jobs.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
public class OracleClient {

	@Autowired
	private WebClient webClient;

	@Value("${service.oracle.url}") 
	private String url;
	

	public Mono<GemCarrier> random() {
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallback(new OracleApi() {
		        @Override
		        public Mono<GemCarrier> random() {
		            return Mono.just(GemCarrier.builder().saying("Can't reach the Oracle").author("Sorry!").build());
		        }
		    })
			.target(OracleApi.class, url);
		
    	return oracle.random();
    }
}

