package guru.bonacci.oogway.sannya.service.qd;


import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.filters.LengthFilter;
import guru.bonacci.oogway.sannya.service.general.Sannyasin;
import guru.bonacci.oogway.sannya.service.steps.CharacterGuardian;
import guru.bonacci.oogway.sannya.service.steps.KeyPhraser;

/**
 * I like GoodReads! It was the first Sannyasin.
 */
@Component
public class QDSeeker implements Sannyasin {

	@Autowired
	private CharacterGuardian characterGuardian;

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private LengthFilter lengthFilter;

	@Autowired
	private QDScraper scraper;

	@Override
	public List<Function<String,String>> preprocessingSteps() {
		return asList(characterGuardian, keyPhraser);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return asList(lengthFilter);
	}

	@Override
	public QDScraper getScraper() {
		return scraper;
	};
}
