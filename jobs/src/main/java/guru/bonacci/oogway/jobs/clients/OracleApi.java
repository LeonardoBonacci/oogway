package guru.bonacci.oogway.jobs.clients;

import org.springframework.web.bind.annotation.GetMapping;

import feign.Headers;
import feign.RequestLine;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Headers({ "Accept: application/json" })
interface OracleApi {

	@GetMapping("")
	@RequestLine("GET /oracle/gems/random")
    Mono<GemCarrier> random();
}

