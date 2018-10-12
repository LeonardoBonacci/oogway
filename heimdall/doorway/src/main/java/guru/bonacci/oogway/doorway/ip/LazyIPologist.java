package guru.bonacci.oogway.doorway.ip;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
@Profile("prod")
public class LazyIPologist implements IIPologist {

	@Override
	public Mono<String> checkUp(String ip) {
		return Mono.just(ip);
	}
}
