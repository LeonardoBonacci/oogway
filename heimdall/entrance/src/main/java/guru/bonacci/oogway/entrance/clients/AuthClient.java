package guru.bonacci.oogway.entrance.clients;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.bonacci.oogway.shareddomain.UserInfo;

@RefreshScope
@FeignClient( name = "${application.name.auth}", 
			  configuration = AuthClientConfig.class)
public interface AuthClient {

	@RequestMapping(value = "/auth/users/user1", method = GET)
    UserInfo user();
}

