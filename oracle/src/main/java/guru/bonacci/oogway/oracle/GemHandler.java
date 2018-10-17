package guru.bonacci.oogway.oracle;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.oracle.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.services.GemService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GemHandler {

	@Autowired
	private GemService serv;

	public Mono<ServerResponse> search(ServerRequest request) {
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		return ok()
        		 .body(serv.search(q)
        				 	.map(GemMapper.MAPPER::fromGem)
        				 	.log("found match!"), GemCarrier.class)
        		 .switchIfEmpty(notFound().build());
    }
	
	@PreAuthorize("hasRole('read')")
	public Mono<ServerResponse> random(ServerRequest request) {
		log.info("Receiving request for a random quote");

		 return ok()
				 .body(serv.random()
						 	.map(GemMapper.MAPPER::fromGem), GemCarrier.class);}
}
