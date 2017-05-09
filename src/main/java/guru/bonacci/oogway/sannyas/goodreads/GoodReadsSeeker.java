package guru.bonacci.oogway.sannyas.goodreads;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.Sannyasin;
import guru.bonacci.oogway.sannyas.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.steps.KeyPhraser;

@Component
public class GoodReadsSeeker implements Sannyasin {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String URL = "http://www.goodreads.com/quotes/tag/";

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
					.map(this::cleanDiv)
					.map(q -> q.text())
					.map(this::strip)
					.collect(Collectors.toList());
	}

	public Elements consult(String searchURL) {
		try {
			Document doc = Jsoup.connect(searchURL).get();
			return doc.select("div.quoteText");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new Elements();
	}

	public String determinePagedURL(String searchStr) {
		String searchURL = URL + searchStr;
		try {
			Document doc = Jsoup.connect(searchURL).get();
			Elements elements = doc.select("span.gap");
			int pageNr = Integer.valueOf(elements.first().nextElementSibling().nextElementSibling().text());
			pageNr = new Random().nextInt(pageNr) + 1;
			return searchURL + "?page=" + pageNr;
		} catch (Exception e) { 
			// Taking the easy way, catching all exceptions.
			// No results or one result: same search string without page
			// Not enough results for a gap between the pagination numbers: same search string without page
			// More than two results: page added
			e.printStackTrace();
		}
		return searchURL;
	}

	private Element cleanDiv(Element el) {
		for (Element e : el.children()) 
			e.remove();
		return el;
	}

	private String strip(String str) {
		return str.substring(str.indexOf("“") + 1, str.lastIndexOf("”"));
	}

	public static void main(String args[]) {
		GoodReadsSeeker seeker = new GoodReadsSeeker();
		List<String> quotes = seeker.seek(args[0]);
		quotes.stream().forEach(System.out::println);
	}
}
