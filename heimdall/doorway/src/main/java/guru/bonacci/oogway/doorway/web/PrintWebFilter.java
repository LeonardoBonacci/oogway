package guru.bonacci.oogway.doorway.web;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PrintWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		serverWebExchange.getRequest().getHeaders().forEach((k,v) -> log.debug(k, v));
		return webFilterChain.filter(serverWebExchange);
	}
}