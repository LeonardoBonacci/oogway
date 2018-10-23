package guru.bonacci.oogway.oracle;

import static guru.bonacci.oogway.oracle.beanmapping.GemMapper.MAPPER;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.services.GemService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class GemHandler {

	private final GemService serv;


	public Mono<ServerResponse> create(ServerRequest request) {
		log.info("Receiving request to create a new gem");

		Mono<Gem> gem = request.bodyToMono(GemCarrier.class).map(MAPPER::toIntGem);
		Mono<String> id = gem.flatMap(g -> serv.create(g));
		return id.flatMap(di -> created(URI.create("gems/" + di)).body(fromObject(di)));

	}

	public Mono<ServerResponse> get(ServerRequest request) {
		log.info("Receiving request to retrieve a gem");

		String id = request.pathVariable("id");
		Mono<GemIdCarrier> gem = serv.retrieve(id).map(MAPPER::toExtIdGem);
		return gem.flatMap(g -> ok().body(fromObject(g)))
									.switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> all(ServerRequest request) {
		log.info("Receiving request to see all...");

		Flux<GemCarrier> gems = serv.all().map(MAPPER::toExtGem);
        return ok().contentType(TEXT_EVENT_STREAM).body(gems, GemCarrier.class);
    }

	public Mono<ServerResponse> update(ServerRequest request) {
		log.info("Receiving request for an update");

		Mono<Gem> gem = request.bodyToMono(GemIdCarrier.class).map(MAPPER::toIntIdGem);
		Mono<Boolean> updated = gem.flatMap(g -> serv.update(g));
		return updated.filter(d -> d)
					  .flatMap(g -> ok().build())
					  .switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> delete(ServerRequest request) {
		log.info("Receiving request to delete a gem");

		String id = request.pathVariable("id");
		Mono<Boolean> deleted = serv.delete(id);
		return deleted.filter(d -> d)
					  .flatMap(g -> ok().build())
					  .switchIfEmpty(notFound().build());
    }

	public Mono<ServerResponse> search(ServerRequest request) {
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Flux<GemCarrier> gems = serv.search(q).map(MAPPER::toExtGem);
        return ok().contentType(TEXT_EVENT_STREAM).body(gems, GemCarrier.class);
    }

	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		Mono<GemCarrier> gem = serv.searchOne(q).map(MAPPER::toExtGem);
		return gem.flatMap(g -> ok().body(fromObject(g)))
									.switchIfEmpty(notFound().build());
    }
	
	public Mono<ServerResponse> random(ServerRequest request) {
		log.info("Receiving request for a random quote");

		 return ok().body(fromObject(serv.random().map(MAPPER::toExtGem)));
	}
}
