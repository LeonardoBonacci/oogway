package guru.bonacci.oogway.doorway;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.events.DoorwayStreams;
import guru.bonacci.oogway.doorway.spectre.SpectreHandler;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableBinding({DoorwayStreams.class, Processor.class})
@IntegrationComponentScan
public class DoorwayServer {

	
	@Bean
	RouterFunction<ServerResponse> routes(DoorwayHandler handler, SpectreHandler spectre) {
		return route(GET("/"), req -> ok().body(Mono.fromSupplier(() -> "Alive!"), String.class))
			.andRoute(GET("/iam/{apikey}/searchone"), handler::searchOne)
			.andRoute(GET("/iam/{apikey}/search").and(accept(TEXT_EVENT_STREAM)), handler::search)
			.andRoute(GET("/iam/{apikey}/{id}"), handler::get)  
			.andRoute(POST("/iam/{apikey}"), handler::create)
			.andRoute(PUT("/iam/{apikey}"), handler::update)
			.andRoute(DELETE("/iam/{apikey}/{id}"), handler::delete)
			.andRoute(GET("/iam/{apikey}").and(accept(TEXT_EVENT_STREAM)), handler::all)
			.andRoute(GET("/spectre").and(accept(TEXT_EVENT_STREAM)), spectre::sink);
	}

	public static void main(String[] args) {
		SpringApplication.run(DoorwayServer.class, args);
	}
}
