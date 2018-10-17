package guru.bonacci.oogway.doorway.spectre;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.events.SpectreGateway;
import guru.bonacci.oogway.doorway.ip.IIPologist;
import guru.bonacci.oogway.shareddomain.COMINT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.spectre.enabled", havingValue = "true")
public class ToSpectre implements Spectre {

	private final IIPologist ipologist;

	private final SpectreGateway gateway;

	
	@Override
	public Mono<Void> eavesdrop(String q, String ip)  {
		log.info(ip + " said '" + q + "'");
		return ipologist.checkUp(ip)
						.flatMap(pi -> Mono.fromRunnable(() -> gateway.send(new COMINT(pi, q))));
	}
}
