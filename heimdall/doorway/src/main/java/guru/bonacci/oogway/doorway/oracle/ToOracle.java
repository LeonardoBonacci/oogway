package guru.bonacci.oogway.doorway.oracle;

import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.UserDetailsMagic;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.oracle.enabled", havingValue = "true")
public class ToOracle implements Oracle {

	private final OracleClient client;

	private final UserDetailsMagic userDetails;

	private final Postponer postponer;

	@Override
	public Mono<GemCarrier> enquire(String q, String apikey) {
		if (isEmpty(q))
			return Mono.just(new GemCarrier("No question no answer..", "oogway"));

		Mono<GemCarrier> gem = client.search(q, userDetails.userDetailsRepository(apikey));
		return gem.switchIfEmpty(Mono.just(new GemCarrier(postponer.saySomething(), "oogway")));
	}
}
