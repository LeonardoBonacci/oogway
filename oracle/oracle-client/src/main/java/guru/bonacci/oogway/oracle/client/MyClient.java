package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feign.hystrix.FallbackFactory;
import guru.bonacci.oogway.oracle.client.MyClient.HystrixClientFallbackFactory;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@FeignClient(name = "oracle-service", fallbackFactory = HystrixClientFallbackFactory.class)
public interface MyClient {


	@RequestMapping(value = "/gems", method = RequestMethod.GET)
	GemCarrier search(@RequestParam("q") String q, @RequestParam(value="by") String author);

	@RequestMapping(method = RequestMethod.GET, value = "/gems/random")
    GemCarrier random();
	

	@Component
	static class HystrixClientFallbackFactory implements FallbackFactory<MyClient> {

		private final Logger logger = getLogger(this.getClass());
		
		@Override
		public MyClient create(Throwable cause) {
			return new MyClient() {

				@Override
				public GemCarrier search(String q, String author) {
			        logger.error("Help!!! Can't reach the oracle...", cause);    
					return null;
				}

				@Override
				public GemCarrier random() {
			        logger.error("Help!!! Can't reach the oracle...", cause);    
					return null;
				}
			};
		}
	}
}

