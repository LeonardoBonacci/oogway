package guru.bonacci.oogway.doorway.oracle;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.AuthClient;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
@ConditionalOnProperty(name = "service.oracle.enabled", havingValue = "true")
public class ToOracle implements Oracle {

	@Autowired
	private OracleClient oracleClient;

	@Autowired
	private AuthClient authClient;

	@Autowired
	private Postponer postponer;

	@Override
	public GemCarrier enquire(String q, String apiKey) {
		if (isEmpty(q))
			return new GemCarrier("No question no answer..", "oogway");

		Credentials currentUser = authClient.user(apiKey);
		Optional<GemCarrier> gem = oracleClient.consult(q, null, currentUser);
		return gem.orElse(new GemCarrier(postponer.saySomething(), "oogway"));
	}
}
