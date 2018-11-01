package guru.bonacci.oogway.doorway.spectre;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@ConditionalOnMissingBean(ToSpectreHandler.class)
@Component
public class OrNotToSpectreHandler extends SpectreHandler {

	@Override
	public Mono<ServerResponse> sink(ServerRequest request) {
		log.info("day off..");
		return ok().body(Mono.fromSupplier(() -> "Out of service.."), String.class);
   	}
}
