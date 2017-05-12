package guru.bonacci.oogway.sannyas.brainyquote;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.PageCache;
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

	@Resource(name = "brainyQuotePageCache")
	private PageCache pageCache;

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
				.map(Element::text)
				.collect(Collectors.toList());
	}

	public String determinePagedURL(String searchStr) {
		String searchURL = URL.replace("#tag#", searchStr);
		Integer nrOfPages = pageCache.getNrOfPages(searchURL.replace("#page#", "1"));
		return searchURL.replace("#page#", String.valueOf(RandomUtils.nextInt(1, nrOfPages + 1)));
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
}
