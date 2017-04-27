package bonacci.oogway.sannyas.steps;

import java.util.List;
import java.util.stream.Collectors;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentenceAlgorithms;

public class KeyPhraser implements PreProcesStep {

	@Override
	public String apply(String input) {
		Sentence sentence = new Sentence(input);
		SentenceAlgorithms algorithms = new SentenceAlgorithms(sentence);
		List<String> output = algorithms.keyphrases();
		return output.stream().collect(Collectors.joining(" "));
	}
}
