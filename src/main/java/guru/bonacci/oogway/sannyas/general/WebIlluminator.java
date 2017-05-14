package guru.bonacci.oogway.sannyas.general;



import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Abstract class with general functionality for accessing web pages and
 * interpreting their content.
 * 
 * Illuminate: to supply or brighten with light; light up. to make lucid or
 * clear; throw light on (a subject). to decorate with lights, as in
 * celebration. to enlighten, as with knowledge. to make resplendent or
 * illustrious: A smile illuminated her face. to decorate (a manuscript, book,
 * etc.) with colors and gold or silver, as was often done in the Middle Ages.
 * 
 * Etymology: Late Middle English: from Latin illuminat- ‘illuminated’, from the
 * verb illuminare, from in- ‘upon’ + lumen, lumin- ‘light’.
 */
public abstract class WebIlluminator {

	/**
	 * To not overly access our dear wisdom suppliers we keep an administration
	 * of already visited urls. 
	 */
	private Set<String> consultedWebPages = Collections.synchronizedSet(new HashSet<>());	
	
	public List<String> find(String... tags) {
		return Arrays.stream(tags)
					.map(this::determineURL)
					.filter(consultedWebPages::add) //returns false when present in set
					.map(this::consultWeb)
					.flatMap(Elements::stream)
					.map(this::procesElement)
					.map(Element::text)
					.map(this::procesText)
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
