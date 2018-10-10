package guru.bonacci.oogway.doorway.web;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class IPWebFilter implements WebFilter {

	private final Logger logger = getLogger(this.getClass());

	public final static String IP_HEADER = "ip";
	
	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		ServerWebExchange theServerWebExchange = serverWebExchange;

		String ip = serverWebExchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
		logger.debug("found ip " + ip); 

		/*
		 * Create a mutable wrapper around the ServerHttpRequest 
		 * since the original ServerHttpRequest object is immutable.
		 */
		final ServerHttpRequest newServerHttpRequest = serverWebExchange.getRequest()
																		.mutate()
																		.header(IP_HEADER, ip)
																		.build();

		/*
		 * In the same way create a mutable wrapper around the ServerWebExchange object 
		 * to set the new ServerHttpRequest on the ServerWebExchange.
		 */
		theServerWebExchange = serverWebExchange.mutate()
												.request(newServerHttpRequest)
												.build();

		return webFilterChain.filter(theServerWebExchange);
	}
}