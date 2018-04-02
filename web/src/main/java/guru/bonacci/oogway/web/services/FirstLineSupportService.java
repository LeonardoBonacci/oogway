package guru.bonacci.oogway.web.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.web.clients.OAuth2RestTemplateFactory;
import guru.bonacci.oogway.web.intercept.WatchMe;

/**
 * Tier I is the initial support level responsible for basic customer issues. It
 * is synonymous with first-line support, level 1 support, front-end support,
 * support line 1, and various other headings denoting basic level technical
 * support functions. The first job of a Tier I specialist is to gather the
 * customer’s information and to determine the customer’s issue by analyzing the
 * symptoms and figuring out the underlying problem. When analyzing the
 * symptoms, it is important for the technician to identify what the customer is
 * trying to accomplish so that time is not wasted on "attempting to solve a
 * symptom instead of a problem."
 */
@Service
public class FirstLineSupportService {

	@Autowired
	private org.springframework.cloud.context.scope.refresh.RefreshScope refreshScope;
	
	@Autowired
	private OAuth2RestTemplateFactory oracleClientFactory;
	
	@Autowired
	private ApplicationContext appContext;

	int i = 0;
	
	public void prepare() {
		i++;
		if (i % 2 == 0)
			oracleClientFactory.setP("password");
		else 
			oracleClientFactory.setP("passwordsss");

		refreshScope.refresh("resource");
		refreshScope.refresh("client");
	}
	
	@WatchMe
	public GemCarrier enquire(String q) {
		if (isEmpty(q))
			return new GemCarrier("No question no answer..", "oogway");

		prepare();
		OAuth2RestTemplate oracleClient = appContext.getBean(OAuth2RestTemplate.class);
		GemCarrier gem = oracleClient.getForObject("http://oracle-service:4444/oracle/gems?q=" + q, GemCarrier.class);
		return gem;

	}
	
//	public Optional<GemCarrier> consult(String q, String author) {
//		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
//		resource.setUsername("user1");
//		resource.setPassword("password");
//		resource.setAccessTokenUri("http://auth-service:5000/auth/oauth/token");
//		resource.setClientId("web-service");
//		resource.setClientSecret("web-service-secret");
//		resource.setGrantType("password");
//
//		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, clientContext);
//
//		GemCarrier gem = restTemplate.getForObject("http://oracle-service:4444/oracle/gems?q=" + q, GemCarrier.class);
//		return Optional.ofNullable(gem);
//	}
}
