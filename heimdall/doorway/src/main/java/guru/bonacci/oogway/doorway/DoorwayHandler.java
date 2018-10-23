package guru.bonacci.oogway.doorway;

import static guru.bonacci.oogway.doorway.web.IPWebFilter.IP_HEADER;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static java.net.URLEncoder.encode;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.bigbang.BigGem;
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

	private final FirstLineSupportService serv;

	private final Spectre spectre;
	
	
	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Mono<BigGem> bg = spectre.eavesdrop(q, request.headers().header(IP_HEADER).get(0))
						 .then(serv.searchOne(q, apikey));
		return bg.flatMap(g -> ok().body(fromObject(g))); // there can only and will always be one
   	}

	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> create(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to create a new gem");

		Mono<GemCarrier> gem = request.bodyToMono(GemCarrier.class);
		Mono<String> id = gem.flatMap(g -> serv.create(g, apikey));
		return id.flatMap(di -> created(URI.create("gems/" + encode(di))).body(fromObject(di)));

	}

	public Mono<ServerResponse> get(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to retrieve a gem");

		String id = request.pathVariable("id");
		Mono<GemIdCarrier> gem = serv.retrieve(id, apikey);
		return gem.flatMap(g -> ok().body(fromObject(g)))
									.switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> all(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to see all...");

		Flux<GemCarrier> gems = serv.all(apikey);
        return ok().contentType(TEXT_EVENT_STREAM).body(gems, GemCarrier.class);
    }

	public Mono<ServerResponse> update(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request for an update");

		Mono<GemIdCarrier> gem = request.bodyToMono(GemIdCarrier.class);
		Mono<Boolean> updated = gem.flatMap(g -> serv.update(g, apikey));
		return updated.filter(d -> d)
					  .flatMap(g -> ok().build())
					  .switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> delete(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		log.info("Receiving request to delete a gem");

		String id = request.pathVariable("id");
		Mono<Boolean> deleted = serv.delete(id, apikey);
		return deleted.filter(d -> d)
					  .flatMap(g -> ok().build())
					  .switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> search(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Flux<GemCarrier> gems = serv.search(q, apikey);
        return ok().contentType(TEXT_EVENT_STREAM).body(gems, GemCarrier.class);
    }
}
