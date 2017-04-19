package bonacci.oogway.parser.goodreads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bonacci.oogway.parser.WebCrawler;
import bonacci.oogway.parser.WisdomGatherer;

public class GoodReadsHunter implements WisdomGatherer, WebCrawler {

	private static final String URL = "http://www.goodreads.com/quotes/tag/";
			
	public static void main(String args[]) {
		GoodReadsHunter gatherer = new GoodReadsHunter();
		List<String> quotes = gatherer.gather(args[0]);
		quotes.stream().forEach(System.out::println);
	}

	@Override
	public List<String> gather(String tag) {
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

	@Override
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
}
