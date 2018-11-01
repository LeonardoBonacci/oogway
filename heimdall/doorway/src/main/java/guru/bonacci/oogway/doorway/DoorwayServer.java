package guru.bonacci.oogway.doorway;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
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
import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import guru.bonacci.oogway.doorway.spectre.SpectreHandler;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableBinding({DoorwayStreams.class, Processor.class})
@IntegrationComponentScan
public class DoorwayServer {

	
	@Bean
	RouterFunction<ServerResponse> routes(DoorwayHandler handler, Lumberjack lumber, SpectreHandler spectre) {
		RouterFunction<ServerResponse> health = 
					route(GET("/"), req -> ok().body(Mono.fromSupplier(() -> "Alive!"), String.class));
		
		return health.and(handler.routes(lumber))
					 .and(spectre.routes());
	}

	
	public static void main(String[] args) {
		SpringApplication.run(DoorwayServer.class, args);
	}
}
