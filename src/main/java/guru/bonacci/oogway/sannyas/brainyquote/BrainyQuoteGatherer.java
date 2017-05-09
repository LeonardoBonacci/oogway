package guru.bonacci.oogway.sannyas.brainyquote;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

	private static final String URL = "https://www.brainyquote.com/quotes/keywords/#tag#_#page#.html?vm=l";
	
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
				.map(this::determinePagedURL)
				.map(this::consult)
				.flatMap(Elements::stream)
				.map(q -> q.text())
				.collect(Collectors.toList());
	}

	public Elements consult(String searchURL) {
		try {
			Document doc = Jsoup.connect(searchURL).userAgent("Mozilla").get();
			return doc.select("a[title='view quote']");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new Elements();
	}

	public String determinePagedURL(String searchStr) {
		String searchURL = URL.replaceAll("#tag#", searchStr);
		Integer pageNr = 1;
		try {
			Document doc = Jsoup.connect(searchURL.replaceAll("#page#", pageNr.toString())).userAgent("Mozilla").get();
			Elements elements = doc.select("ul.pagination a");
			pageNr = Integer.valueOf(elements.get(elements.size() - 2).text()); //last page is second last element
			pageNr = new Random().nextInt(pageNr) + 1;
		} catch (Exception e) { 
			// Taking the easy way, catching all exceptions.
			// No results or one result: same search string without page
			// Not enough results for a gap between the pagination numbers: same search string without page
			// More than two results: page added
			e.printStackTrace();
		}
		return searchURL.replaceAll("#page#", pageNr.toString());
	}

	public static void main(String args[]) {
		BrainyQuoteGatherer gatherer = new BrainyQuoteGatherer();
		List<String> quotes = gatherer.seek(args[0]);
		quotes.stream().forEach(System.out::println);
	}
}
