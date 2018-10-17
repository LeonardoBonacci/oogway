package guru.bonacci.oogway.sannyas.steps;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.split;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Removes duplicate words
 */
@Component
@Slf4j
public class DuplicateRemover implements Function<String,String> {

	@Override
	public String apply(String input) {
		log.debug("in: " + input);

		String[] words = split(input);
		Set<String> uniqueWords = new LinkedHashSet<>();
	    Collections.addAll(uniqueWords, words);
	    String output = uniqueWords.stream().collect(joining(" "));
	    
		log.debug("out: " + output);
		return output;
	}
}
