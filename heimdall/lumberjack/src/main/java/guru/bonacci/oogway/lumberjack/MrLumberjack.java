package guru.bonacci.oogway.lumberjack;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class MrLumberjack {

	@Bean
	RouterFunction<ServerResponse> routes(LogHandler handler) {
		return route(GET("/"), req -> ok().body(Mono.fromSupplier(() -> "Alive!"), String.class))
				.andRoute(GET("/visits/{apikey}"), handler::insert);
	}

	public static void main(String[] args) {
		SpringApplication.run(MrLumberjack.class, args);
	}

	@EnableWebFluxSecurity
	@EnableReactiveMethodSecurity
	class SecurityConfig {

		@Bean
		SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
			return http
                    .authorizeExchange()
                    .anyExchange().authenticated()
                    .and().authenticationManager(reactiveAuthenticationManager())
                    .httpBasic().and()
                    .build();
		}

		@Bean
		ReactiveAuthenticationManager reactiveAuthenticationManager() {
			return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
		}

		@SuppressWarnings("deprecation")
		@Bean
		public MapReactiveUserDetailsService userDetailsRepository() {
			UserDetails doorway = User.withDefaultPasswordEncoder().username("dw").password("wd").roles("read").build();
			return new MapReactiveUserDetailsService(doorway);
		}
	}

}
