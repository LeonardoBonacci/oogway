package guru.bonacci.oogway.doorway.ip;

import reactor.core.publisher.Mono;

public interface IIPologist {

	public Mono<String> checkUp(String ip);
}
