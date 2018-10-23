package guru.bonacci.oogway.doorway;

import static guru.bonacci.oogway.doorway.web.IPWebFilter.IP_HEADER;
import static java.net.URLEncoder.encode;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.bigbang.BigGem;
import guru.bonacci.oogway.doorway.clients.NotFoundException;
import guru.bonacci.oogway.doorway.clients.UnauthorizedException;
import guru.bonacci.oogway.doorway.services.FirstLineSupportService;
import guru.bonacci.oogway.doorway.spectre.Spectre;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class DoorwayHandler {

	private static final String FALLBACK = "The oracle has a sick day, try again later...";
	
	private final FirstLineSupportService serv;

	private final Spectre spectre;
	
	
	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Mono<BigGem> bg = spectre.eavesdrop(q, request.headers().header(IP_HEADER).get(0))
					 .then(serv.searchOne(q, apikey));
		return bg.flatMap(g -> ok().body(fromObject(g)))
				.onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
				.onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
   	}

	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> create(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to create a new gem");

		Mono<GemCarrier> gem = request.bodyToMono(GemCarrier.class);
		Mono<String> id = gem.flatMap(g -> serv.create(g, apikey));
		return id.flatMap(di -> created(URI.create("gems/" + encode(di))).body(fromObject(di)))
				  .onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
				  .onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
	}

	public Mono<ServerResponse> get(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to retrieve a gem");

		String id = request.pathVariable("id");
		Mono<GemIdCarrier> gem = serv.retrieve(id, apikey);
		return gem.flatMap(g -> ok().body(fromObject(g)))
					.onErrorResume(NotFoundException.class, e -> notFound().build())
					.onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
					.onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
    }

	public Mono<ServerResponse> update(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request for an update");

		Mono<GemIdCarrier> gem = request.bodyToMono(GemIdCarrier.class);
		Mono<Void> updated = gem.flatMap(g -> serv.update(g, apikey));
		return updated.flatMap(voit -> ok().build())
						  .onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
						  .onErrorResume(NotFoundException.class, e -> notFound().build())
						  .onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
    }

	public Mono<ServerResponse> delete(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to delete a gem");

		String id = request.pathVariable("id");
		Mono<Void> deleted = serv.delete(id, apikey);
		return deleted.flatMap(g -> ok().build())
						  .onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
						  .onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
    }

	public Mono<ServerResponse> all(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to see all...");

		Flux<GemCarrier> gems = serv.all(apikey).limitRequest(25);
		// no flowing gems here... 
		Mono<List<GemCarrier>> mg = gems.collectList();
        return mg.flatMap(g -> ok().contentType(TEXT_EVENT_STREAM).body(fromObject(g)))
        					.onErrorResume(UnauthorizedException.class, e -> status(UNAUTHORIZED).build())
        					.onErrorResume(e -> ok().contentType(TEXT_PLAIN).body(fromObject(FALLBACK)));
    }

	public Mono<ServerResponse> search(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Flux<GemCarrier> gems = serv.search(q, apikey).limitRequest(1);
		return ok().contentType(TEXT_EVENT_STREAM).body(gems, GemCarrier.class);
    }
}
