package guru.bonacci.oogway.jobs.tutor.clients;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class OracleClient {

	private final WebClient webClient;

	@Value("${service.oracle.url}") 
	private String url;
	

	public Mono<GemCarrier> random() {
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new FallbackOracleApi(cause))
			.target(OracleApi.class, url);
		
    	return oracle.random();
    }
	
	@SuppressWarnings("deprecation")
	public Mono<GemCarrier> search(String searchString) {
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
			.setFallbackFactory(cause -> new FallbackOracleApi(cause))
			.target(OracleApi.class, url);
		
    	return oracle.search(URLEncoder.encode(searchString));
    }
	
	private class FallbackOracleApi implements OracleApi {

		private final Throwable cause;
	
		FallbackOracleApi (Throwable t) {
			cause = t;
		}
		
		@Override
        public Mono<GemCarrier> search(String q) {
        	log.error(cause.getMessage());
            return Mono.fromSupplier(() -> new GemCarrier("Can't reach the Oracle", "Sorry!"));
        }

		@Override
		public Mono<GemCarrier> random() {
        	log.error(cause.getMessage());
			return null;
		}
	}	
}

