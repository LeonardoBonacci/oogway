package guru.bonacci.oogway.doorway.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Profile("!prod")
public class IPologist implements IIPologist {

	private Flux<String> iperator;

	@Autowired
	public IPologist(IPerable iperable) {
		iperator = Flux.fromIterable(iperable);
	}

	@Override
	public Mono<String> checkUp(String ipIn) {
		return iperator.next();
	}
}
