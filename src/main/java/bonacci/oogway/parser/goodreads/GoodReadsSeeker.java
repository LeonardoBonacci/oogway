package bonacci.oogway.parser.goodreads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import bonacci.oogway.parser.Sannyasin;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentenceAlgorithms;

@Component
public class GoodReadsSeeker implements Sannyasin {

	private static final String URL = "http://www.goodreads.com/quotes/tag/";
			
	public static void main(String args[]) {
		GoodReadsSeeker gatherer = new GoodReadsSeeker();
		List<String> quotes = gatherer.seek(args[0]);
		quotes.stream().forEach(System.out::println);
	}

	@Override
	public List<Function<String, String>> preproces() {
		return Arrays.asList(
								in -> keyphrases(in)
							);
	}

	private String keyphrases(String input) {
		Sentence sentence = new Sentence(input);
		SentenceAlgorithms algorithms = new SentenceAlgorithms(sentence);
		List<String> output = algorithms.keyphrases();
		return output.stream().collect(Collectors.joining(" "));
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
}
