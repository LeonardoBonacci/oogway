package guru.bonacci.oogway.doorway.web;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

import java.util.List;
import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class GreedFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

	private final Lumberjack lumber;

	@Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) {
		List<PathContainer.Element> es = serverRequest.pathContainer().elements();
		log.info("user " + es.get(3));

		return lumber.isGreedy(es.get(3).toString())
				.flatMap(greedy -> {
					if (greedy) {
						return ServerResponse.status(TOO_MANY_REQUESTS).build();
					} else 
						return handlerFunction.handle(serverRequest);
				});

	}
}