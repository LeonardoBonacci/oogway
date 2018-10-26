package guru.bonacci.oogway.doorway.services;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import guru.bonacci.oogway.doorway.bigbang.BigBanger;
import guru.bonacci.oogway.doorway.bigbang.BigGem;
import guru.bonacci.oogway.doorway.oracle.Oracle;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
@Slf4j
@RequiredArgsConstructor
@Service
public class FirstLineSupportService {

	private final BigBanger bbr;
	
	private final Function<String, Oracle> oracleFactory;
	

	public Mono<BigGem> searchOne(String q, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.findOne(q)
					.doOnEach(gem -> log.info("oracle responded: " + gem))
					.zipWith(bbr.everSince(), (gem,since) -> BigGem.builder().gem(gem).bigbang(since).build());
	}
	
	public Mono<String> create(GemCarrier gem, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.insert(gem);
	}

	public Mono<GemIdCarrier> retrieve(String id, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.findById(id);
	}

	public Flux<GemIdCarrier> all(String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.all();
	}

	public Mono<Void> update(GemIdCarrier gem, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.update(gem);
	}

	public Mono<Void> delete(String id, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.delete(id);
	}

	public Flux<GemCarrier> search(String q, String apikey) {
		Oracle oracle = oracleFactory.apply(apikey);
		return oracle.find(q).onErrorReturn(
				new GemCarrier("Generally speaking, the errors in religion are dangerous; those in philosophy only ridiculous.", "David Hume"));
	}
}
