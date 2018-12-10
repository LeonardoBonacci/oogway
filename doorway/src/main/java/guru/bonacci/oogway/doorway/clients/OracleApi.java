package guru.bonacci.oogway.doorway.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import guru.bonacci.oogway.domain.GemCarrier;
import reactor.core.publisher.Mono;

interface OracleApi {

	
	@RequestLine("GET /gems/searchone?q={q}")
	@Headers({ "Accept: application/json" })
    Mono<GemCarrier> searchOne(@Param("q") String q);
}

