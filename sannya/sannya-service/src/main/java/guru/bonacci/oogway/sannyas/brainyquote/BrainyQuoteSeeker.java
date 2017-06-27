package guru.bonacci.oogway.sannyas.brainyquote;


import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.split;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.KeyPhraser;
import guru.bonacci.oogway.sannyas.steps.Lemmatizor;

/**
 * They say: Share our extensive collection of famous quotes by authors,
 * celebrities, newsmakers, and more. Enjoy our Quotes of the Day on the web,
 * Facebook, and blogs.
 */
@Component
public class BrainyQuoteSeeker implements Sannyasin {

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private Lemmatizor lemmatizor;

	@Autowired
	private LengthFilter lengthFilter;

	@Autowired
	private BrainyQuoteIlluminator finder;

	@Override
	public List<Function<String, String>> preprocessingSteps() {
		return asList(keyPhraser, lemmatizor);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return asList(lengthFilter);
	}

	@Override
	public List<String> seek(String tagsAsString) {
		String[] tags = split(tagsAsString);
		return finder.find(tags);
	}
}
