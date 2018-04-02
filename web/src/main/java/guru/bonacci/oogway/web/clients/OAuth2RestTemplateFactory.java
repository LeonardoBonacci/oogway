package guru.bonacci.oogway.web.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@Configuration
public class OAuth2RestTemplateFactory {

	@Bean
	@Scope("prototype")
	public OAuth2RestTemplate oracleClient() {
		return new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
	}

	@Bean
	@Scope("prototype")
	public ResourceOwnerPasswordResourceDetails resourceDetails() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername("user1");
		resource.setPassword("password");
		resource.setAccessTokenUri("http://auth-service:5000/auth/oauth/token");
		resource.setClientId("web-service");
		resource.setClientSecret("web-service-secret");
		resource.setGrantType("password");
		return resource;
	}	
}

