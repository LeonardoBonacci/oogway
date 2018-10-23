package guru.bonacci.oogway.doorway.oracle;

import static org.springframework.util.StringUtils.isEmpty;

import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.UserDetailsMagic;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
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
			return Mono.fromSupplier(() -> GemCarrier.builder().saying("No question no answer..").author("oogway").build());

		Mono<GemCarrier> gem = client.searchOne(q, userDetails.userDetailsRepository(apikey));
		return gem.switchIfEmpty(Mono.fromSupplier(() -> GemCarrier.builder().saying(postponer.saySomething()).author("oogway").build()));
	}

	@Override
	public Mono<String> insert(GemCarrier gem) {
		return client.create(gem, userDetails.userDetailsRepository(apikey));
	}

	@Override
	public Mono<Boolean> update(GemIdCarrier gem) {
		return client.update(gem, userDetails.userDetailsRepository(apikey));
	}

	@Override
	public Mono<Boolean> delete(String id) {
		return client.delete(id, userDetails.userDetailsRepository(apikey));
	}

	@Override
	public Mono<GemIdCarrier> findById(String id) {
		return client.findById(id, userDetails.userDetailsRepository(apikey));
	}

	@Override
	public Flux<GemCarrier> all() {
		return client.all(userDetails.userDetailsRepository(apikey));
	}

	@Override
	public Flux<GemCarrier> find(String q) {
		return client.search(q, userDetails.userDetailsRepository(apikey));
	}
}
