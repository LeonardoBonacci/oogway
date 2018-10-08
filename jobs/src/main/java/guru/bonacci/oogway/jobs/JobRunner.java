package guru.bonacci.oogway.jobs;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class JobRunner {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
        				.filter(basicAuthentication("jobs","sboj"))
        				.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(JobRunner.class, args);
    }
    

    @EnableWebFluxSecurity
    @EnableReactiveMethodSecurity
    class SecurityConfig {

        @Bean
        SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        	return http
        			.authorizeExchange()
        				.pathMatchers(HttpMethod.GET, "/tweet/**").permitAll()
    				.and()
        			.build();
        }
    }
}