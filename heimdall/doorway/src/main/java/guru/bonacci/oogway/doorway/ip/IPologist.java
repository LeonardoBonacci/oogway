package guru.bonacci.oogway.doorway.ip;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
@Profile("!prod")
public class IPologist implements IIPologist {

	private Iterator<String> iperator;
	
	@Autowired
	public IPologist(IPerable iperable) {
		iperator = iperable.iterator();
	}

	@Override
	public Mono<String> checkUp(String ip) {
		return Mono.defer(() -> Mono.just(iperator.next()));
	}
}
