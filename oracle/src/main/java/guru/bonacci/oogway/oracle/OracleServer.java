package guru.bonacci.oogway.oracle;

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

@Import(ElasticConfig.class)
@SpringBootApplication
public class OracleServer { 
	
	
	@Bean
	RouterFunction<ServerResponse> routes(GemHandler handler) {
		return handler.routes();
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
            		.csrf().disable()
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
    		UserDetails oogway = User.withDefaultPasswordEncoder().username("oogway").password("yawgoo").roles("read").build();
    		return new MapReactiveUserDetailsService(oogway);
    	}
    }
}
