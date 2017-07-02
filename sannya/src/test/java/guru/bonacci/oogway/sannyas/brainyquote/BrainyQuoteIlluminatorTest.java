package guru.bonacci.oogway.sannyas.brainyquote;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class BrainyQuoteIlluminatorTest {

	@Spy
	BrainyQuoteIlluminator finder;

	@Test
	public void shouldRetrieveQuotes() throws IOException {
		Document doc = parse(readToString("brainyquote/brainyquote-mock-faith.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<String> found = finder.find("faith");
		found.forEach(System.out::println);
		List<String> result = readToList("brainyquote/brainyquote-quotes-faith.txt");

		assertEquals(result, found);
	}

	@Test
	public void shouldFindNumerOfPagesWhenMany() throws IOException {
		Document doc = parse(readToString("brainyquote/brainyquote-mock-faith.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the faith url");
		assertThat(nrOfPages, is(equalTo(39)));
	}

	@Test
	public void shouldReturnOneWhenNoPages() throws IOException {
		Document doc = parse(readToString("brainyquote/brainyquote-mock-aaaaa.txt"));
		doReturn(doc).when(finder).get(anyString());

		Integer nrOfPages = finder.getNrOfPages("the aaaaa url");
		assertThat(nrOfPages, is(equalTo(1)));
	}

}
