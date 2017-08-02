package guru.bonacci.oogway.sannya.service.processing;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.client.GemDataCarrier;
import guru.bonacci.oogway.sannya.service.filters.ProfanityFilter;
import guru.bonacci.oogway.sannya.service.general.Sannyasin;

/**
 * “Every aspect of your life is anchored energetically in your living space, so
 * clearing clutter can completely transform your entire existence.” 
 * - Karen Kingston
 */
@Component
public class CleaningAgent {

	@Autowired
	private ProfanityFilter profanityFilter;

	public List<GemDataCarrier> noMoreClutter(Sannyasin sannya, List<GemDataCarrier> found) {
		Predicate<String> postfiltering = sannya.postfilteringStep().stream()
															  		.reduce(p -> true, Predicate::and);		
		return found.stream()
			 .filter(gem -> postfiltering.test(gem.getSaid()))
			 .filter(gem -> profanityFilter.test(gem.getSaid()))
			 .collect(toList());
	}
}
