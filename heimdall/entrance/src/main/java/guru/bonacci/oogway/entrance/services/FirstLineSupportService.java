package guru.bonacci.oogway.entrance.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.entrance.bigbrother.WatchMe;
import guru.bonacci.oogway.entrance.cheaters.Postponer;
import guru.bonacci.oogway.entrance.clients.AuthClient;
import guru.bonacci.oogway.entrance.clients.OracleClient;
import guru.bonacci.oogway.entrance.security.Credentials;
import guru.bonacci.oogway.entrance.security.EntranceDecoder;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.UserInfo;

/**
 * Tier I is the initial support level responsible for basic customer issues. It
 * is synonymous with first-line support, level 1 support, front-end support,
 * support line 1, and various other headings denoting basic level technical
 * support functions. The first job of a Tier I specialist is to gather the
 * customer’s information and to determine the customer’s issue by analyzing the
 * symptoms and figuring out the underlying problem. When analyzing the
 * symptoms, it is important for the technician to identify what the customer is
 * trying to accomplish so that time is not wasted on "attempting to solve a
 * symptom instead of a problem."
 */
@Service
public class FirstLineSupportService {

	@Autowired
	private OracleClient oracleClient;

	@Autowired
	private AuthClient authClient;

	@Autowired
	private EntranceDecoder decoder;

	@Autowired
	private Postponer postponer;

	@WatchMe //TODO interceptor will contain the check on nr. of hits
	public GemCarrier enquire(String q) {
		if (isEmpty(q))
			return new GemCarrier("No question no answer..", "oogway");

		UserInfo currentUser = authClient.user();
		String encryptedPassword = "oeVBzKWf0CTNawGWluG7lXZ+ov1ZLQTwbJKivIAtMa+i3o1z1XVOAUvk+2Azmpj5aJp0U62oIXhE2R3WIGzI7tkMnCXYC+fpA2WnPE7rRoZ8klL/Q+m6vVKZT1jZCga4bFtm8FYEewVkz4MWHLWHdZpJB2BVixCE48APnDVniLA=";
		String password = decoder.decode(encryptedPassword);
		Optional<GemCarrier> gem = oracleClient.consult(q, null, new Credentials("user1", password));
		return gem.orElse(new GemCarrier(postponer.saySomething(), "oogway"));
	}
}
