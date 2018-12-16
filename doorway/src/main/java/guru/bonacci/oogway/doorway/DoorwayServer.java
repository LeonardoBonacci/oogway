package guru.bonacci.oogway.doorway;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.doorway.events.Binding;
import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import reactor.core.publisher.Mono;

@EnableBinding(Binding.class)
@SpringBootApplication
public class DoorwayServer {

	
	@Bean
	RouterFunction<ServerResponse> routes(DoorwayHandler handler, Lumberjack lumber) {
		RouterFunction<ServerResponse> health = 
					route(GET("/"), req -> ok().body(Mono.fromSupplier(() -> "Alive!"), String.class));
		
		return health.and(handler.routes(lumber));
	}

	
	public static void main(String[] args) {
		SpringApplication.run(DoorwayServer.class, args);
	}
}
