package guru.bonacci.oogway.oracle;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.bonacci.oogway.oracle.services.GemHandler;

@SpringBootApplication
@EnableElasticsearchRepositories
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableResourceServer
public class OracleServer { //extends ResourceServerConfigurerAdapter {

//	@Autowired
//	private ResourceServerProperties sso;

	
	@Bean
	RouterFunction<ServerResponse> routes(GemHandler handler) {
		return route(GET("/gems"), handler::search)
 			.andRoute(GET("/gems/random"), handler::random);
	}

//	@Bean
//	public OAuth2RestTemplate restTemplate() {
//		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
//		return new OAuth2RestTemplate(resource);
//	}
//	
//	@Bean
//	public ResourceServerTokenServices tokenServices() {
//		CustomUserInfoTokenServices serv = new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
//		serv.setRestTemplate(restTemplate());
//		return serv;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(OracleServer.class, args);
	}

}
