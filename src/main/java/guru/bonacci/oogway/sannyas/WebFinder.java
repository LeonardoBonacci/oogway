package guru.bonacci.oogway.sannyas;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class WebFinder {

	public List<String> find(String... tags) {
		return Arrays.stream(tags)
					.map(this::determineURL)
					.map(this::consultWeb)
					.flatMap(Elements::stream)
					.map(this::procesElement)
					.map(Element::text)
					.map(this::procesText)
					.collect(Collectors.toList());
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
	 * Pre-process before retrieving text from the element
	 * Override for specific processing behavior
	 * @param el
	 * @return
	 */
	protected Element procesElement(Element el) {
		return el;
	}

	/**
	 * Post-process after retrieving text from the element
	 * Override for specific processing behavior
	 * @param str
	 * @return
	 */
	protected String procesText(String str) {
		return str;
	}
}
