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

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.secretdomain.GemDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class BQScraperTest {

	@Spy
	BQScraper finder;

	@Test
	public void shouldRetrieveQuotes() throws IOException {
		String in = "I say unto you 'Jesus': here comes the sun";
		
		String TALK_TO = "I say unto you";
		String TALK_TO_REG_EXPR = "I say unto you '\\w+':.*";

		System.out.println(in.startsWith(TALK_TO) && in.matches(TALK_TO_REG_EXPR));
		
		int fromBy = in.indexOf("'") + 1;
		int closeBy = in.indexOf("'", fromBy);
		String by = in.substring(fromBy, closeBy);
	
		int fromQuestion = in.indexOf(":") + 1;
		String question = StringUtils.trim(in.substring(fromQuestion));

		
		Document doc = parse(readToString("bq/bq-mock-faith.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemDTO> found = finder.find("faith");
		List<String> quotes = found.stream().map(GemDTO::getSaying).collect(toList());

		List<String> expected = readToList("bq/bq-quotes-faith.txt");
		assertEquals(expected, quotes);
	}

	@Test
	public void shouldRetrieveAuthors() throws IOException {
		Document doc = parse(readToString("bq/bq-mock-faith.txt"));
		doReturn("does not matter").when(finder).determineURL(anyString());
		doReturn(doc).when(finder).get(anyString());

		List<GemDTO> found = finder.find("faith");
		assertThat(found.get(0).getAuthor(), is(equalTo("Helen Keller")));
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
