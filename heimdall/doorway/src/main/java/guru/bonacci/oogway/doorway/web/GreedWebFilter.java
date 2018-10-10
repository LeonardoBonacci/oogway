package guru.bonacci.oogway.doorway.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import guru.bonacci.oogway.doorway.lumber.Lumberjack;
import reactor.core.publisher.Mono;

@Component
public class GreedWebFilter implements WebFilter {

	@Autowired
	private Lumberjack lumber;

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		List<PathContainer.Element> es = serverWebExchange.getRequest().getPath().elements();
		if (es.size() < 4 || !es.get(1).value().equals("iam")) 	
			return Mono.empty();

		
		return lumber.isGreedy("iam")
				.flatMap(greedy -> {
					if (greedy) {
						serverWebExchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
						return Mono.empty();
					} else 
						return webFilterChain.filter(serverWebExchange);
				});

	}
}