package guru.bonacci.oogway.doorway.oracle;

import static org.springframework.util.StringUtils.isEmpty;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.NotFoundException;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.UserDetailsMagic;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ToOracle implements Oracle {

	private final OracleClient client;

	private final UserDetailsMagic userDetails;

	private final Postponer postponer;

	private final String apikey;

	
	@Override
	public Mono<GemCarrier> findOne(String q) {
		if (isEmpty(q))
			return Mono.fromSupplier(() -> new GemCarrier("No question no answer..", "oogway"));
		
		Mono<GemCarrier> gem = client.searchOne(q, userDetails.userDetailsRepository(apikey));
		return gem.onErrorReturn(NotFoundException.class, new GemCarrier(postponer.saySomething(), "oogway"));
	}
}
