package guru.bonacci.oogway.jobs.clients;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private WebClient webClient;

	@Value("${service.oracle.url}") 
	private String url;
	

	public Mono<GemCarrier> random() {
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new FallbackOracleApi(cause))
			.target(OracleApi.class, url);
		
    	return oracle.random();
    }
	
	public Mono<GemCarrier> search(String searchString) {
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new FallbackOracleApi(cause))
			.target(OracleApi.class, url);
		
    	return oracle.search(searchString);
    }
	
	class FallbackOracleApi implements OracleApi {

		Throwable cause;
	
		FallbackOracleApi (Throwable t) {
			cause = t;
		}
		
		@Override
        public Mono<GemCarrier> search(String q) {
        	logger.error(cause.getMessage());
            return Mono.just(GemCarrier.builder().saying("Can't reach the Oracle").author("Sorry!").build());
        }

		@Override
		public Mono<GemCarrier> random() {
        	logger.error(cause.getMessage());
			return null;
		}
	}	
}

