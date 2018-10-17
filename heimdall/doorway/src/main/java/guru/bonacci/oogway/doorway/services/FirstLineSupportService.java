package guru.bonacci.oogway.doorway.services;

import org.springframework.stereotype.Service;

import guru.bonacci.oogway.doorway.oracle.Oracle;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Service
@Slf4j
@RequiredArgsConstructor
public class FirstLineSupportService {

	private final Oracle oracle;

	
	public Mono<GemCarrier> enquire(String q, String apikey) {
		return oracle.enquire(q, apikey)
					.doOnEach(gem -> log.info("oracle responded: " + gem));
	}
}
