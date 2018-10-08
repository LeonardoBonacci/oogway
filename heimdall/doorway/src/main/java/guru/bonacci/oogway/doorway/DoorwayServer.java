package guru.bonacci.oogway.doorway;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.events.DoorwayEventChannels;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableBinding(DoorwayEventChannels.class)
@IntegrationComponentScan
public class DoorwayServer {

	@Bean
	RouterFunction<ServerResponse> routes(DoorwayHandler handler) {
		return route(GET("/"), req -> ok().body(fromPublisher(Mono.just("Alive!"), String.class)))
			.andRoute(GET("/iam/{apikey}"), handler::search);
	}

	public static void main(String[] args) {
		SpringApplication.run(DoorwayServer.class, args);
	}
}
