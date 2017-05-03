package bonacci.oogway.sannyas.goodreads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bonacci.oogway.sannyas.Sannyasin;
import bonacci.oogway.sannyas.filters.LengthFilter;
import bonacci.oogway.sannyas.steps.KeyPhraser;

@Component
public class GoodReadsSeeker implements Sannyasin {

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
	public List<String> seek(String tag) {
		List<String> result = new ArrayList<>();
		try {
			Elements quotes = crawl(tag);
			result = quotes.stream()
					  .map(this::cleanDiv)
					  .map(q -> q.text())
					  .map(this::strip)
					  .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}

	public Elements crawl(String searchStr) throws IOException {
		Document doc = Jsoup.connect(URL + searchStr).get();
		return doc.select("div.quoteText");
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
		GoodReadsSeeker gatherer = new GoodReadsSeeker();
		List<String> quotes = gatherer.seek(args[0]);
		quotes.stream().forEach(System.out::println);
	}
}
