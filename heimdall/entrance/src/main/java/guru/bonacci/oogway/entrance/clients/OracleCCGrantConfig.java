package guru.bonacci.oogway.entrance.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Profile("!unit-test") //hack :)
public class OracleCCGrantConfig {

    @Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
    
	OAuth2RestTemplate restTemplate() {
		return new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
	}

	ClientCredentialsResourceDetails resourceDetails() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		return resource;
	}	
}

