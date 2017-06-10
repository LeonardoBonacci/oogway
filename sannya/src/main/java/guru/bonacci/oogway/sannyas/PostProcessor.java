package guru.bonacci.oogway.sannyas;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.es.Gem;
import guru.bonacci.oogway.sannyas.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;

//TODO
@Component
public class PostProcessor {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private ProfanityFilter profanityFilter;

	public Iterable<Gem> awayWithTheClutter(Sannyasin sannya, List<String> found) {
		Predicate<String> postfiltering = sannya.postfilteringStep().stream()
															  		.reduce(p -> true, Predicate::and);		
		Stream<Gem> postfiltered = found.stream()
			 .filter(postfiltering)
			 .filter(profanityFilter)
			 .peek(f -> logger.info("About to index wisdom: '" + f + "'")) 
			 .map(Gem::new);

		return postfiltered::iterator;
	}
}
