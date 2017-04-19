package bonacci.oogway.parser.goodreads;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bonacci.oogway.parser.WisdomGatherer;

public class GoodReadsHunter implements WisdomGatherer {

	public static void main(String argv[]) {
		GoodReadsHunter gatherer = new GoodReadsHunter();
		try {
			List<String> quotes = gatherer.gather();
			quotes.stream().forEach(System.out::println);
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> gather() throws IOException {
		Document doc = Jsoup.connect("http://www.goodreads.com/quotes/tag/balcony").get();
		Elements quotes = doc.select("div.quoteText");
		return quotes.stream()
			  .map(this::cleanDiv)
			  .map(q -> q.text())
			  .map(this::strip)
			  .collect(Collectors.toList());
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
