package guru.bonacci.oogway.sannyas.services;

import static java.util.function.Function.identity;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;

/**
 * “Rationalization is foreplay with one's conscience.” 
 * ― Doug Cooper
 */
@Component
public class ForePlayer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	public DuplicateRemover duplicateRemover;

	public String play(Sannyasin sannya, String input) {
		Function<String,String> preprocessing = sannya.preprocessingSteps().stream()
																		   .reduce(identity(), Function::andThen);
		preprocessing = preprocessing.andThen(duplicateRemover);
		String preprocessedInput = preprocessing.apply(input);
		logger.info(sannya.getClass() + "- Preprocessed input: '" + preprocessedInput + "'");
		return preprocessedInput;
	}	
}
