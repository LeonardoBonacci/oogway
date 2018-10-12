package guru.bonacci.oogway.jobs.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Headers({ "Accept: application/json" })
interface OracleApi {

	@RequestLine("GET /gems?q={q}")
    Mono<GemCarrier> search(@Param("q") String q);

	@RequestLine("GET /gems/random")
    Mono<GemCarrier> random();
}
