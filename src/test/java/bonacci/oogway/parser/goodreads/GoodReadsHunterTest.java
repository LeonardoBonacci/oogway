package bonacci.oogway.parser.goodreads;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.mockito.Matchers;

public class GoodReadsHunterTest {

	@Test
	public void shouldRetrieveQuotesFromWebPage() throws IOException {
		GoodReadsHunter hunter = mock(GoodReadsHunter.class);

		doReturn(crawlFile()).when(hunter).crawl("ease");
		when(hunter.gather(Matchers.anyString())).thenCallRealMethod();
		
		List<String> ares = hunter.gather("ease");
		List<String> shouldBees = readLinesFromFile("goodreads-ease-quotes.txt");

		for (int i=0; i<ares.size(); i++) {
			System.out.println(ares.get(i));
			System.out.println(shouldBees.get(i));
		}
		//TODO compare somehow, asserThat/contains and AsserEquals are not workings	
	}

	private Elements crawlFile() throws IOException {
		File input = readFile("goodreads-ease-mock.txt");
		Document doc = Jsoup.parse(input, "UTF-8");
		return doc.select("div.quoteText");
	}

	
	private File readFile(String fileName) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
	
	private List<String> readLinesFromFile(String fileName) throws IOException {
		return FileUtils.readLines(readFile(fileName), "utf-8");
	}
}
