package guru.bonacci.oogway.sannyas.steps;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentenceAlgorithms;

/**
 * Determines the important words from a phrase
 * 
 * Phrases or a search term that is made up of multiple keywords, or a specific
 * combination of keywords, that a user would enter into a search engine. In SEO
 * (search engine optimization), optimizing your site for specific keyphrases
 * will yield a smaller number of more specific and relevant traffic.
 */
@Component
public class KeyPhraser implements Function<String,String> {

	@Override
	public String apply(String input) {
		Sentence sentence = new Sentence(input);
		SentenceAlgorithms algorithms = new SentenceAlgorithms(sentence);
		List<String> output = algorithms.keyphrases();
		return output.stream().collect(joining(" "));
	}
}
