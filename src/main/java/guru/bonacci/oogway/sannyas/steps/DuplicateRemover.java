package guru.bonacci.oogway.sannyas.steps;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang.StringUtils.split;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;

/**
 * Removes duplicate words
 */
@Component
public class DuplicateRemover implements Function<String,String> {

	@Override
	public String apply(String input) {
		String[] words = split(input);
		Set<String> uniqueWords = new LinkedHashSet<>();
	    Collections.addAll(uniqueWords, words);
		return uniqueWords.stream().collect(joining(" "));
	}
}
