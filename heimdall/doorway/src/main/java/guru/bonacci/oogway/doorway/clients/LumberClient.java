package guru.bonacci.oogway.doorway.clients;

import java.net.URLEncoder;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class LumberClient {

	private final BiFunction<String, String, WebClient> webClientFactory;

	@Value("${service.lumberjack.url}") 
	private String url;
	
	
	@SuppressWarnings("deprecation")
	public Mono<Long> visits(String apikey) {
		WebClient webClient = webClientFactory.apply("dw", "wd");

		LumberApi lumber = CloudReactiveFeign.<LumberApi>builder(webClient)
			.setFallbackFactory(cause -> new LumberApi() {
		        @Override
		        public Mono<Long> visits(String apikey) {
		        	log.error(cause.getMessage());
		            return Mono.just(1l);
		        }
		    })
			.target(LumberApi.class, url);
		
    	return lumber.visits(URLEncoder.encode(apikey));
	}
}

