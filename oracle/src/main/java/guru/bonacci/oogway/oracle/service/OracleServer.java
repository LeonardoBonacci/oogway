package guru.bonacci.oogway.oracle.service;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.oracle.service.security.CustomUserInfoTokenServices;

@SpringBootApplication
@EnableAsync
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class OracleServer extends ResourceServerConfigurerAdapter {

	private final Logger logger = getLogger(this.getClass());
	
	@Autowired
	private ResourceServerProperties sso;

	public static void main(String[] args) {
		SpringApplication.run(OracleServer.class, args);
	}

	@Bean
	CommandLineRunner demo(Environment env, GemRepository repo) {
		return args -> {
			// creative exclusion, is it not?
//			if (env.acceptsProfiles("!unit-test")) {
				try {
					Gem[] friedrichsBest = readToList("nietzsche.txt").stream()
																		.map(quote -> new Gem(quote, "Friedrich Nietzsche"))
																		.toArray(Gem[]::new);
					repo.saveTheNewOnly(friedrichsBest);
				} catch (IOException e) {
					logger.error("Nietzsche!!", e);
				}
//			}	
		};
	}
	
	@LoadBalanced
	@Bean
	public OAuth2RestTemplate loadBalancedTemplate() {
		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
		return new OAuth2RestTemplate(resource);
	}
	
	@Bean
	public ResourceServerTokenServices tokenServices() {
		CustomUserInfoTokenServices serv = new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
		serv.setRestTemplate(loadBalancedTemplate());
		return serv;
	}
}
