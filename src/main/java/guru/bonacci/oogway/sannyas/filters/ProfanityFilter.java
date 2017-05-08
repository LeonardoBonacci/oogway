package guru.bonacci.oogway.sannyas.filters;

import java.io.IOException;
import java.util.function.Predicate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProfanityFilter implements Predicate<String>  {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String URL = "http://www.purgomalum.com/service/containsprofanity?text=";

	@Override
	public boolean test(String quote) {
		try {
			Document doc = Jsoup.connect(URL + quote).get();
			logger.info(doc.body().text());
			boolean containsprofanity = Boolean.valueOf(doc.body().text());
			if (containsprofanity)
				logger.info("Blocking this indecent saying: " + quote);
			return !containsprofanity;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return true;
		}
	}
}
