package guru.bonacci.oogway.sannyas.brainyquote;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.steps.KeyPhraser;

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
	private LengthFilter lengthFilter;

	@Autowired
	private BrainyQuoteIlluminator finder;

	@Override
	public List<Function<String, String>> preprocessingSteps() {
		return Arrays.asList(keyPhraser::apply);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return Arrays.asList(lengthFilter);
	}

	@Override
	public List<String> seek(String tagsAsString) {
		String[] tags = StringUtils.split(tagsAsString);
		return finder.find(tags);
	}
}
