package guru.bonacci.oogway.lumberjack;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;
import reactor.core.publisher.Mono;

@Component
public class LogHandler {

	@Autowired
	private LogService serv;

	public Mono<ServerResponse> insert(ServerRequest request) {
		String apikey = request.pathVariable("apikey");
		return ok().body(fromPublisher(
							serv.insert(Log.builder().apiKey(apikey).build()), Long.class));
   	}
}
