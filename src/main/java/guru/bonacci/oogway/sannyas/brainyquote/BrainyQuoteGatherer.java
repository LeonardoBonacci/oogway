package guru.bonacci.oogway.sannyas.brainyquote;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.Sannyasin;
import guru.bonacci.oogway.sannyas.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.steps.KeyPhraser;

@Component
public class BrainyQuoteGatherer implements Sannyasin {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String URL = "https://www.brainyquote.com/search_results.html?q=";

	@Autowired
	private KeyPhraser keyPhraser;

	@Autowired
	private LengthFilter lengthFilter;

	@Override
	public List<Function<String, String>> preproces() {
		return Arrays.asList(keyPhraser::apply);
	}

	@Override
	public List<Predicate<String>> postfilters() {
		return Arrays.asList(lengthFilter);
	}

	@Override
	public List<String> seek(String tagsAsString) {
		String[] tags = StringUtils.split(tagsAsString);
		return Arrays.stream(tags)
				.map(this::consult)
				.flatMap(Elements::stream)
				.map(q -> q.text())
				.collect(Collectors.toList());
	}

	public Elements consult(String searchStr) {
		try {
			Document doc = Jsoup.connect(URL + searchStr).userAgent("Mozilla").get();
			return doc.select("a[title='view quote']");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new Elements();
	}

	public static void main(String args[]) {
		BrainyQuoteGatherer gatherer = new BrainyQuoteGatherer();
		List<String> quotes = gatherer.seek(args[0]);
		quotes.stream().forEach(System.out::println);
	}
}
