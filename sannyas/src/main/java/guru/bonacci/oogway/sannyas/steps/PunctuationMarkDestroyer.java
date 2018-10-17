package guru.bonacci.oogway.sannyas.steps;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Removes...
 */
@Component
@Slf4j
public class PunctuationMarkDestroyer implements Function<String,String> {

	@Override
	public String apply(String input) {
		log.debug("in: " + input);

	    String output = input;
	    
		log.debug("out: " + output);
		return output;
	}
}
