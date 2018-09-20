package guru.bonacci.oogway.doorway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "${service.lumberjack.name}",
			  url = "${service.lumberjack.url}")
public interface LumberjackClient {

	@GetMapping(value = "/lumber/visits/{apikey}")
    Long visits(@PathVariable("apikey") String apiKey);
}