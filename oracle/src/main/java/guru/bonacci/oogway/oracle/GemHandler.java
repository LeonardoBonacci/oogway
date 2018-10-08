package guru.bonacci.oogway.oracle;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.oracle.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.services.GemService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
public class GemHandler {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemService serv;

    @PreAuthorize("hasRole('read')")
	public Mono<ServerResponse> search(ServerRequest request) {
		String q = request.queryParam("q").orElse("nothing matches this string");
		logger.info("Receiving request for a wise answer on: '" + q + "'");

//		return ok().contentType(MediaType.TEXT_EVENT_STREAM)
		return ok()
        		 .body(serv.search(q)
        				 	.map(GemMapper.MAPPER::fromGem)
        				 	.log("found match!"), GemCarrier.class)
        		 .switchIfEmpty(notFound().build());
    }

    @PreAuthorize("hasRole('read')")
	public Mono<ServerResponse> random(ServerRequest request) {
		logger.info("Receiving request for a random quote");

//		return ok().contentType(MediaType.TEXT_EVENT_STREAM)
//        		 .body(serv.random()
//        				 	.map(GemMapper.MAPPER::fromGem), GemCarrier.class);
		return ok()
       		 .body(Mono.just(new GemCarrier("bla", "bla")), GemCarrier.class);
    }
}
