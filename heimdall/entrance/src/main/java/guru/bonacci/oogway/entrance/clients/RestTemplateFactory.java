package guru.bonacci.oogway.entrance.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.entrance.security.Credentials;


@Component
public class RestTemplateFactory {

	@Autowired
	private ApplicationContext appContext;

	public RestTemplate oAuth2PasswordGrantRestTemplate(Credentials credentials) {
		OraclePasswordGrantClientConfig config = appContext.getBean(OraclePasswordGrantClientConfig.class);
		OAuth2RestTemplate template = config.restTemplate();
		ResourceOwnerPasswordResourceDetails resource = (ResourceOwnerPasswordResourceDetails)template.getResource();
		resource.setUsername(credentials.getUsername());
		resource.setPassword(credentials.getPw());
		return template;
	}
}
