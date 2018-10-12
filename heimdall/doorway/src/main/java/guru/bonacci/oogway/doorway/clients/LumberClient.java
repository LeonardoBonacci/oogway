package guru.bonacci.oogway.doorway.clients;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Mono;

@Component
public class LumberClient {

	private final Logger logger = getLogger(this.getClass());
	
	@Autowired
	private BiFunction<String, String, WebClient> webClientFactory;

	@Value("${service.lumberjack.url}") 
	private String url;
	
	public Mono<Long> visits(String apikey) {
		WebClient webClient = webClientFactory.apply("dw", "wd");

		LumberApi lumber = CloudReactiveFeign.<LumberApi>builder(webClient)
			.setFallbackFactory(cause -> new LumberApi() {
		        @Override
		        public Mono<Long> visits(String apikey) {
		        	logger.error(cause.getMessage());
		            return Mono.just(1l);
		        }
		    })
			.target(LumberApi.class, url);
		
    	return lumber.visits(apikey);
	}
}

