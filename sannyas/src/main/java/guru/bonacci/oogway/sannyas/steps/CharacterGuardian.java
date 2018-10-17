package guru.bonacci.oogway.sannyas.steps;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Keeps letters, numbers and spaces
 */
@Component
@Slf4j
public class CharacterGuardian implements Function<String,String> {

	@Override
	public String apply(String input) {
		log.debug("in: " + input);

		String output = input.replaceAll("[^\\w\\s]","");
	    
		log.debug("out: " + output);
		return output;
	}
}
