package guru.bonacci.oogway.doorway;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.doorway.clients.UnauthorizedException;
import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import guru.bonacci.oogway.doorway.services.FirstLineSupportService;
import guru.bonacci.oogway.doorway.web.GreedFilterFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class DoorwayHandler {

	private static final String FALLBACK = "The oracle has a sick day, try again later...";
	
	private final FirstLineSupportService serv;


	public RouterFunction<ServerResponse> routes(Lumberjack lumber) {
		RouterFunction<ServerResponse> jsons = 
				nest(accept(APPLICATION_JSON), 
						route(GET("/searchone"), this::searchOne));

		return nest(path("/iam/{apikey}"), jsons.filter(new GreedFilterFunction(lumber)));
	}

	
	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Mono<GemCarrier> bg = serv.searchOne(q, apikey);
		return bg.flatMap(g -> ok().body(fromObject(g)))
				.onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
				.onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
   	}
}
