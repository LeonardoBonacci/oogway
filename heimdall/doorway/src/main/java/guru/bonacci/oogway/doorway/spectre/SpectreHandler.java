package guru.bonacci.oogway.doorway.spectre;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface SpectreHandler {

	public Mono<ServerResponse> sink(ServerRequest request);
}
