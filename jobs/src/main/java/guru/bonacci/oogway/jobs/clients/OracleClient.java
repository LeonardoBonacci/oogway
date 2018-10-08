package guru.bonacci.oogway.jobs.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;

@Component
public class OracleClient {

	@Autowired
	private WebClient webClient;

	//TODO property
	public Mono<GemCarrier> random() {
		OracleApi oracle = WebReactiveFeign.<OracleApi>builder(webClient).target(OracleApi.class, "http://oracle-service:4444");
    	return oracle.random();
    }
}

