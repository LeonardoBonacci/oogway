package bonacci.oogway.sannyas.steps;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentenceAlgorithms;

@Component
public class KeyPhraser implements PreProcesStep {

	@Override
	public String apply(String input) {
		Sentence sentence = new Sentence(input);
		SentenceAlgorithms algorithms = new SentenceAlgorithms(sentence);
		List<String> output = algorithms.keyphrases();
		return output.stream().collect(Collectors.joining(" "));
	}
}
