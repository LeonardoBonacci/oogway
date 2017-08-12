package guru.bonacci.oogway.sannya.service.general;



import static java.util.Arrays.stream;
import static java.util.Collections.synchronizedSet;
import static java.util.stream.Collectors.toList;
import static org.jsoup.Jsoup.connect;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Abstract class with general functionality for accessing web pages and
 * interpreting their content.
 * 
 * 'oogway's ugly looking code is in my subclasses'
 */
public abstract class WebScraper {

	/**
	 * To not overly access our dear wisdom suppliers we keep an administration
	 * of already visited urls. 
	 */
	private Set<String> consultedWebPages = synchronizedSet(new HashSet<>());	
	
	public List<GemCarrier> find(String... tags) {
		return stream(tags)
					.map(this::determineURL)
					.filter(consultedWebPages::add) //returns false when present in set
					.map(this::consultWeb)
					.flatMap(Elements::stream)
					.map(this::toGem)
					.collect(toList());
	}

	/**
	 * Construct the url based on the search string
	 * @param searchStr
	 * @return
	 */
	protected abstract String determineURL(String searchStr);

	/**
	 * Access the web page
	 * @param searchURL
	 * @return
	 */
	protected abstract Elements consultWeb(String searchURL);

	/**
	 * Retrieving info from element
	 * @param el
	 * @return
	 */
	protected abstract GemCarrier toGem(Element el);

	/**
	 * Method to facilitate testing
	 */
	public Document get(String searchURL) throws IOException {
		return connect(searchURL).userAgent("Mozilla").get();
	}
}
