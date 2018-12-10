package guru.bonacci.oogway.oracle;

import static guru.bonacci.oogway.oracle.beanmapping.GemMapper.MAPPER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.oracle.services.GemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class GemHandler {

	private final GemService serv;


	public RouterFunction<ServerResponse> routes() {
		RouterFunction<ServerResponse> jsons = 
				nest(accept(APPLICATION_JSON), 
						route(GET("/searchone"), this::searchOne));
		return nest(path("/gems"), jsons);
	}

		
	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Mono<GemCarrier> gem = serv.searchOne(q).map(MAPPER::toExtGem);
		return gem.flatMap(g -> ok().body(fromObject(g)));
    }
}
