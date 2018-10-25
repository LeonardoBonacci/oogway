package guru.bonacci.oogway.doorway.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface OracleApi {

	
	@RequestLine("GET /gems/searchone?q={q}")
	@Headers({ "Accept: application/json" })
    Mono<GemCarrier> searchOne(@Param("q") String q);
	
	@RequestLine("GET /gems/search?q={q}")
	@Headers({ "Accept: text/event-stream" })
    Flux<GemCarrier> search(@Param("q") String q);
	
	@RequestLine("GET /gems/{id}")
	@Headers({ "Accept: application/json" })
    Mono<GemIdCarrier> retrieve(@Param("id") String id);
	
	@RequestLine("POST /gems")
	@Headers({ "Content-Type: application/json" })
    Mono<String> create(GemCarrier gem);

	@RequestLine("PUT /gems")
	@Headers({ "Content-Type: application/json" })
    Mono<Void> update(GemIdCarrier gem);

	@RequestLine("DELETE /gems/{id}")
	@Headers({ "Accept: application/json" })
    Mono<Void> delete(@Param("id") String id);

	@RequestLine("GET /gems")
	@Headers({ "Accept: text/event-stream" })
    Flux<GemIdCarrier> all();
}

