package guru.bonacci.oogway.doorway;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static guru.bonacci.oogway.doorway.web.IPWebFilter.IP_HEADER;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.services.FirstLineSupportService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
public class DoorwayHandler {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private FirstLineSupportService serv;

	public Mono<ServerResponse> search(ServerRequest request) {
		//TODO this is the client's ip address
		System.out.println(request.headers().header(IP_HEADER).get(0));
		
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		logger.info("Receiving request for a wise answer on: '" + q + "'");

		return ok().body(fromPublisher(serv.enquire(q, apikey), GemCarrier.class));
   	}
}
