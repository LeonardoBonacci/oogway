package guru.bonacci.oogway.web.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@Configuration
public class OAuth2RestTemplateFactory {

	String pw = "password";
	public void setP(String pw) {
		this.pw = pw;
	}
	
	@RefreshScope
	@Bean("client")
	@Scope("prototype")
	public OAuth2RestTemplate oracleClient(@Value("${pw:password}") String pw) {
		return new OAuth2RestTemplate(resourceDetails(pw), new DefaultOAuth2ClientContext());
	}

	@RefreshScope
	@Bean
	@Scope("prototype")
	public ResourceOwnerPasswordResourceDetails resourceDetails(@Value("${pw:password}") String pw) {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername("user1");
		resource.setPassword(pw);
		resource.setAccessTokenUri("http://auth-service:5000/auth/oauth/token");
		resource.setClientId("web-service");
		resource.setClientSecret("web-service-secret");
		resource.setGrantType("password");
		return resource;
	}	
}

