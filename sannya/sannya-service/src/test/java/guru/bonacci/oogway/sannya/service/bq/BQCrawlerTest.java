package guru.bonacci.oogway.sannya.service.bq;

import static guru.bonacci.oogway.utils.MyFileUtils.readToList;
import static guru.bonacci.oogway.utils.MyFileUtils.readToString;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.jsoup.Jsoup.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.client.GemDataCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class BQCrawlerTest {

	@Spy
	BQCrawler finder;

	@Test
	public void shouldRetrieveQuotes() throws IOException {
		Document doc = parse(readToString("bq/bq-mock-faith.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemDataCarrier> found = finder.find("faith");
		List<String> quotes = found.stream().map(GemDataCarrier::getSaid).collect(toList());

		List<String> expected = readToList("bq/bq-quotes-faith.txt");
		assertEquals(expected, quotes);
	}

	@Test
	public void shouldRetrieveAuthors() throws IOException {
		Document doc = parse(readToString("bq/bq-mock-faith.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemDataCarrier> found = finder.find("faith");
		assertThat(found.get(0).getBy(), is(equalTo("Helen Keller")));
	}

	@Test
	public void shouldFindNumerOfPagesWhenMany() throws IOException {
		Document doc = parse(readToString("bq/bq-mock-faith.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the faith url");
		assertThat(nrOfPages, is(equalTo(39)));
	}

	@Test
	public void shouldReturnOneWhenNoPages() throws IOException {
		Document doc = parse(readToString("bq/bq-mock-aaaaa.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the aaaaa url");
		assertThat(nrOfPages, is(equalTo(1)));
	}

}
