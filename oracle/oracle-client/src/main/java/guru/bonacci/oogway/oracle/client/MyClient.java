package guru.bonacci.oogway.oracle.client;

import java.util.Optional;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@FeignClient(name = "oracle-service")
public interface MyClient {

	@RequestMapping(value = "/gems", method = RequestMethod.GET)
	GemCarrier search(@RequestParam("q") String q, @RequestParam(value="by", required=false) Optional<String> author);
	 
	@RequestMapping(method = RequestMethod.GET, value = "/gems/random")
    GemCarrier random();
}

