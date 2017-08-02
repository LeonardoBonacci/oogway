package guru.bonacci.oogway.sannya.service.gr;

import static guru.bonacci.oogway.utils.MyFileUtils.readToList;
import static guru.bonacci.oogway.utils.MyFileUtils.readToString;
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

import guru.bonacci.oogway.sannya.service.gr.GRCrawler;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class GRCrawlerTest {

	@Spy
	GRCrawler finder;
	
	@Test
	public void shouldRetrieveQuotes() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-romance.txt"));
		doReturn(doc).when(finder).get(anyString());

		List<String> found = finder.find("romance");
		List<String> result = readToList("gr/gr-quotes-romance.txt");

		assertEquals(result, found);
	}
	
	@Test
	public void shouldFindNumerOfPagesWhenMany() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-romance.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the romance url");
		assertThat(nrOfPages, is(equalTo(100)));
	}

	@Test
	public void shouldFindNumberWhenFew() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-some.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the romance url");
		assertThat(nrOfPages, is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnOneWhenNoPages() throws IOException {
		Document doc = parse(readToString("gr/gr-mock-aaaaa.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the aaaaa url");
		assertThat(nrOfPages, is(equalTo(1)));
	}
}
