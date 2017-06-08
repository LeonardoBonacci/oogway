package guru.bonacci.oogway.sannyas;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;

//TODO some name
@Component
public class PreProcessor {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private DuplicateRemover duplicateRemover;

	public String goForIt(Sannyasin sannya, String input) {
		Function<String,String> preprocessing = sannya.preprocessingSteps().stream()
																		   .reduce(Function.identity(), Function::andThen);
		preprocessing.andThen(duplicateRemover);

		String preprocessedInput = preprocessing.apply(input);
		logger.info(sannya.getClass() + "- Preprocessed input: '" + preprocessedInput + "'");
		return preprocessedInput;
	}	
}
