package guru.bonacci.oogway.doorway.spectre;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.events.SpectreGateway;
import guru.bonacci.oogway.doorway.ip.IIPologist;
import guru.bonacci.oogway.shareddomain.COMINT;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnProperty(name = "service.spectre.enabled", havingValue = "true")
public class ToSpectre implements Spectre {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private IIPologist ipologist;

	@Autowired
	private SpectreGateway gateway;

	@Override
	public Mono<Void> eavesdrop(String q, String ip)  {
		logger.info(ip + " said '" + q + "'");
		return ipologist.checkUp(ip)
						.flatMap(pi -> Mono.fromRunnable(() -> gateway.send(new COMINT(pi, q))));
	}
}
