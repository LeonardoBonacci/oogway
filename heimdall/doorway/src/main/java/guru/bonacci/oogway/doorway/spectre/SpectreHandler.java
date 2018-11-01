package guru.bonacci.oogway.doorway.spectre;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public abstract class SpectreHandler {


	public RouterFunction<ServerResponse> routes() {
		return route(GET("/spectre").and(accept(TEXT_EVENT_STREAM)), this::sink);
	}

	
	public abstract Mono<ServerResponse> sink(ServerRequest request);
}
