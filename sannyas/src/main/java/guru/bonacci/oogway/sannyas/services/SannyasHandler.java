package guru.bonacci.oogway.sannyas.services;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
public class SannyasHandler {

	@Autowired
	private SannyasService service;

	
	public Mono<ServerResponse> feed(ServerRequest request) {
		String q = request.queryParam("q").orElse("");
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
        		   .body(service.feed(q).log(), GemCarrier.class);
    }
}
