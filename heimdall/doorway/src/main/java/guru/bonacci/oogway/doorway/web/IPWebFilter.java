package guru.bonacci.oogway.doorway.web;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class IPWebFilter implements WebFilter {

	public final static String IP_HEADER = "ip";
	
	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		ServerWebExchange theServerWebExchange = serverWebExchange;

		String ip = serverWebExchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
		log.debug("found ip " + ip); 

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