package guru.bonacci.oogway.doorway;

import static guru.bonacci.oogway.doorway.web.IPWebFilter.IP_HEADER;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.services.FirstLineSupportService;
import guru.bonacci.oogway.doorway.spectre.Spectre;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DoorwayHandler {

	@Autowired
	private FirstLineSupportService serv;

	@Autowired
	private Spectre spectre;
	
	public Mono<ServerResponse> searchOne(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		String q = request.queryParam("q").orElse("nothing matches this string");
		log.info("Receiving request for a wise answer on: '" + q + "'");

		return ok().body(spectre.eavesdrop(q, request.headers().header(IP_HEADER).get(0))
					  .then(serv.enquire(q, apikey)), GemCarrier.class);
   	}
}
