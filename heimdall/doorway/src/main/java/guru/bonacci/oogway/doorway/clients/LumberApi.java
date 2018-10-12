package guru.bonacci.oogway.doorway.clients;

import feign.Param;
import feign.RequestLine;
import reactor.core.publisher.Mono;

interface LumberApi {

	@RequestLine("GET /visits/{apikey}")
    Mono<Long> visits(@Param("apikey") String apikey);
}

