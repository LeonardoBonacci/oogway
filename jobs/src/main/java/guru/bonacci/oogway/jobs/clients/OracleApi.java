package guru.bonacci.oogway.jobs.clients;

import feign.Headers;
import feign.RequestLine;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Headers({ "Accept: application/json" })
interface OracleApi {

	@RequestLine("GET /gems/random")
    Mono<GemCarrier> random();
}

