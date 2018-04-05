package guru.bonacci.oogway.entrance.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Profile("!unit-test") //hack :)
public class PasswordGrantConfig {

    @Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
    
    // Sad but true, my preferred solution to use RefreshScope in order to have a 
    // thread-bound username-password unique OAuth2RestTemplate didn't work (I ran out of patience) 
    // Hystrix intercepts and recalls resourceDetails() a few times when executing the rest call.
	OAuth2RestTemplate restTemplate() {
		return new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
	}

	ResourceOwnerPasswordResourceDetails resourceDetails() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType("password");
		return resource;
	}	
}

