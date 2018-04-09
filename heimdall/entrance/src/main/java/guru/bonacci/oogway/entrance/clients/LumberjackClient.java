package guru.bonacci.oogway.entrance.clients;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RefreshScope
@FeignClient( name = "lumberjack")
public interface LumberjackClient {

	@RequestMapping(value = "/visits/{apikey}", method = GET)
    Long visits(@PathVariable("apikey") String apiKey);
}