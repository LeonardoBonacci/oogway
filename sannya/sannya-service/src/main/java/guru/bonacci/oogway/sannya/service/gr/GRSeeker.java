package guru.bonacci.oogway.sannya.service.gr;


import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.split;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.filters.LengthFilter;
import guru.bonacci.oogway.sannya.service.general.Sannyasin;
import guru.bonacci.oogway.sannya.service.steps.CharacterGuardian;
import guru.bonacci.oogway.sannya.service.steps.KeyPhraser;
import guru.bonacci.oogway.shareddomain.GemDTO;

/**
 * I like GoodReads! It was the first Sannyasin.
 */
@Component
public class GRSeeker implements Sannyasin {

	@Autowired
	private CharacterGuardian characterGuardian;

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private LengthFilter lengthFilter;

	@Autowired
	private GRScraper scraper;

	@Override
	public List<Function<String,String>> preprocessingSteps() {
		return asList(characterGuardian, keyPhraser);
	}

	@Override
	public List<Predicate<String>> postfilteringStep() {
		return asList(lengthFilter);
	}

	@Override
	public List<GemDTO> seek(String tagsAsString) {
		String[] tags = split(tagsAsString);
		return scraper.find(tags);
	}
}