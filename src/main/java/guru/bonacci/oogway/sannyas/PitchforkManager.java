package guru.bonacci.oogway.sannyas;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.es.Gem;
import guru.bonacci.oogway.es.OracleRepo;
import guru.bonacci.oogway.sannyas.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;

/**
 * A manager alone cannot perform all the tasks assigned to him. In order to
 * meet the targets, the manager should delegate authority. Delegation of
 * Authority means division of authority and powers downwards to the
 * subordinate. Delegation is about entrusting someone else to do parts of your
 * job. Delegation of authority can be defined as subdivision and sub-allocation
 * of powers to the subordinates in order to achieve effective results.
 * 
 * People who manage by a pitchfork are doing so with a heavy and often
 * controlling hand: demanding progress, forcing accountability, prodding and
 * pushing for results through the use of threats and fear tactics. This style
 * of tough, ruthless management is painful for people who are put in a position
 * where they are pushed to avoid consequences rather than pulled toward a
 * desired goal.
 */
@Component
public class PitchforkManager {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private OracleRepo repository;

	@Autowired
	private DuplicateRemover duplicateRemover;

	@Autowired
	private ProfanityFilter profanityFilter;

	public void delegate(String input) {
		logger.info("About to analyzer input: '" + input + "'");

		// We take a random Sannyasin
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		Sannyasin sannya = sannyas.get(nextInt(0, sannyas.size()));

		// Seeking consists of four steps
		// pre-proces the input
		Function<String,String> preprocessing = sannya.preprocessingSteps().stream()
																		   .reduce(Function.identity(), Function::andThen);
		preprocessing.andThen(duplicateRemover);

		String preprocessedInput = preprocessing.apply(input);
		logger.info(sannya.getClass() + "- Preprocessed input: '" + preprocessedInput + "'");

		// acquire wisdom
		List<String> found = sannya.seek(preprocessedInput);

		// filter the wisdom..
		Predicate<String> postfiltering = sannya.postfilteringStep().stream()
															  		.reduce(p -> true, Predicate::and);
		postfiltering.and(profanityFilter);
		
		Stream<Gem> postfiltered = found.stream()
			 .filter(postfiltering)
			 .peek(f -> logger.info("Indexing wisdom: '" + f + "'")) 
			 .map(Gem::new);

		// ..and bulk persist it
		Iterable<Gem> it = postfiltered::iterator;
		repository.save(it);
	}
}
