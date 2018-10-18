package guru.bonacci.oogway.oracle;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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

import guru.bonacci.oogway.relastic.ElasticConfig;

@SpringBootApplication
@Import(ElasticConfig.class)
public class OracleServer { 
	
	@Bean
	RouterFunction<ServerResponse> routes(GemHandler handler) {
		return route(GET("/gems"), handler::search)
				.andRoute(GET("/gems/random"), handler::random);
	}

	public static void main(String[] args) {
		SpringApplication.run(OracleServer.class, args);
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
        ReactiveAuthenticationManager reactiveAuthenticationManager(){
            return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
        }
        
    	@SuppressWarnings("deprecation")
		@Bean
    	public MapReactiveUserDetailsService userDetailsRepository() {
    		UserDetails jobs = User.withDefaultPasswordEncoder().username("jobs").password("sboj").roles("read").build();
    		UserDetails oogway = User.withDefaultPasswordEncoder().username("oogway").password("yawgoo").roles("read").build();
    		UserDetails user1 = User.withDefaultPasswordEncoder().username("user1").password("1resu").roles("read").build();
    		UserDetails app = User.withDefaultPasswordEncoder().username("app").password("ppa").roles("read").build();
    		UserDetails alfred = User.withDefaultPasswordEncoder().username("alfred").password("derfla").roles("read", "write").build();
    		return new MapReactiveUserDetailsService(jobs, oogway, user1, app, alfred);
    	}
    }
}
