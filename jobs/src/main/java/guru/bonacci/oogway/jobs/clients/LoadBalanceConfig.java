package guru.bonacci.oogway.jobs.clients;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;


@Configuration
public class LoadBalanceConfig {

	//https://github.com/spring-cloud/spring-cloud-security/issues/61#issuecomment-287376098
	@Bean
	UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(SpringClientFactory springClientFactory) {
	    return template -> {
	        AccessTokenProviderChain accessTokenProviderChain = Stream
	                .of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
	                        new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
	                .peek(tp -> tp.setRequestFactory(new RibbonClientHttpRequestFactory(springClientFactory)))
	                .collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
	        template.setAccessTokenProvider(accessTokenProviderChain);
	    };
	}
}
