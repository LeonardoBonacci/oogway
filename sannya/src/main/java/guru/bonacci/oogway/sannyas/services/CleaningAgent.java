package guru.bonacci.oogway.sannyas.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.Gem;
import guru.bonacci.oogway.sannyas.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;

@Component
public class CleaningAgent {

	@Autowired
	private ProfanityFilter profanityFilter;

	public List<Gem> noMoreClutter(Sannyasin sannya, List<String> found) {
		Predicate<String> postfiltering = sannya.postfilteringStep().stream()
															  		.reduce(p -> true, Predicate::and);		
		return found.stream()
			 .filter(postfiltering)
			 .filter(profanityFilter)
			 .map(Gem::new)
			 .collect(toList());
	}
}
