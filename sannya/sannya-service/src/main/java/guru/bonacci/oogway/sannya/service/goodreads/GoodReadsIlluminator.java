package guru.bonacci.oogway.sannya.service.goodreads;


import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.general.PageCache;
import guru.bonacci.oogway.sannya.service.general.WebIlluminator;

/**
 * Most popular quote:
 * “Don't cry because it's over, smile because it happened.” ― Dr. Seuss
 */
@Component
public class GoodReadsIlluminator extends WebIlluminator implements PageCache {

	private final Logger logger = getLogger(this.getClass());

	@Value("${web.url.goodreads:http://www.goodreads.com/quotes/tag/}")
	private String url;

	@Override
	public String determineURL(String searchStr) {
		String searchURL = url + searchStr;
		Integer nrOfPages = getNrOfPages(searchURL);
		return searchURL + "?page=" + nextInt(1, nrOfPages + 1);
	}

	@Override
    @Cacheable("pages")
	public Integer getNrOfPages(String searchURL) {
		int pageNr = 1;
		try {
			Document doc = get(searchURL);
			Element element = doc.select("span.previous_page").first();
			while (element != null) {
				try {
					pageNr = Integer.parseInt(element.text());
			    } catch (NumberFormatException ignore) {}

				element = element.nextElementSibling();
			}
		} catch (Exception e) { 
			// Taking the easy way, catching all exceptions.
			// No results or one result: page 1
			// Not enough results for a gap between the pagination numbers: page 1
			// More than two results: page x
			logger.error("Not too many pages, return 1: " + e.getMessage());
		}
		return pageNr;
    }

	@Override
	public Elements consultWeb(String searchURL) {
		try {
			logger.debug("Firing request " + searchURL);
			Document doc = get(searchURL);
			return doc.select("div.quoteText");
		} catch (IOException e) {
			logger.error("Something went wrong. No stress, it does not need to be serieus: " + e.getMessage());
		}
		return new Elements();
	}

	@Override
	public Element procesElement(Element el) {
		for (Element e : el.children()) 
			e.remove();
		return el;
	}

	@Override
	public String procesText(String str) {
		return str.substring(str.indexOf("“") + 1, str.lastIndexOf("”"));
	}
}
