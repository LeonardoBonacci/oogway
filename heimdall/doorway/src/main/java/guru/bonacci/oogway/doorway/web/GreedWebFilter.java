package guru.bonacci.oogway.doorway.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class GreedWebFilter implements WebFilter {

	private final Lumberjack lumber;

	
	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		List<PathContainer.Element> es = serverWebExchange.getRequest().getPath().elements();
		if (es.size() < 4 || !es.get(1).value().equals("iam")) 	
			return webFilterChain.filter(serverWebExchange);

		log.info("user " + es.get(3));
		return lumber.isGreedy(es.get(3).toString())
				.flatMap(greedy -> {
					if (greedy) {
						serverWebExchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
						return Mono.empty();
					} else 
						return webFilterChain.filter(serverWebExchange);
				});

	}
}