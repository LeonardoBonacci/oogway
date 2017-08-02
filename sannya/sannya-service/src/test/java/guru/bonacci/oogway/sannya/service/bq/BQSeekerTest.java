package guru.bonacci.oogway.sannya.service.bq;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.client.GemDataCarrier;
import guru.bonacci.oogway.sannya.service.filters.LengthFilter;
import guru.bonacci.oogway.sannya.service.steps.CharacterGuardian;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class BQSeekerTest {

	@Autowired
	BQSeeker seeker;
	
	@MockBean
	CharacterGuardian characterGuardian;

	@MockBean
	LengthFilter lengthFilter;

	@MockBean
	BQCrawler finder;

	@Test
	public void shouldPreProcess() {
		String in = "some input";
		String out = "some output";
		
		when(characterGuardian.apply(in)).thenReturn(out);
		assertThat(seeker.preprocessingSteps().get(0).apply(in), is(equalTo(out)));
	}
	
	@Test
	public void shouldPostFilter() {
		String in = "some input";
		boolean out = true;
		
		when(lengthFilter.test(in)).thenReturn(out);
		assertThat(seeker.postfilteringStep().get(0).test(in), is(equalTo(out)));
	}

	@Test
	public void shouldSeek() {
		List<GemDataCarrier> out = asList(new GemDataCarrier("a"), new GemDataCarrier("b"));
		
		when(finder.find("a", "b")).thenReturn(out);
		assertThat(seeker.seek("a b"), is(equalTo(out)));
	}

}
