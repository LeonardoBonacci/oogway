package guru.bonacci.oogway.sannya.service.processing;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannya.service.filters.ProfanityFilter;
import guru.bonacci.oogway.sannya.service.goodreads.GoodReadsSeeker;
import guru.bonacci.oogway.sannya.service.processing.CleaningAgent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class CleaningAgentTest {

	@Autowired
	CleaningAgent agent;

	@MockBean 
	ProfanityFilter profanityFilter;

	@MockBean 
	GoodReadsSeeker sannyasin;

	@Test
    public void shouldCleanSimple() {
		when(sannyasin.postfilteringStep()).thenReturn(asList(i -> true));
		when(profanityFilter.test("one")).thenReturn(true);

		List<String> result = agent.noMoreClutter(sannyasin, asList("one" ,"two", "three"));
		assertThat(result, hasSize(1));
		assertThat(result.get(0), is(equalTo("one")));
	}
	
	@Test
    public void shouldCleanComplex() {
		when(sannyasin.postfilteringStep()).thenReturn(asList(i -> i.equals("one") || i.equals("more than one") || i.equals("two"), i -> true));
		when(profanityFilter.test(contains("one"))).thenReturn(true);

		List<String> result = agent.noMoreClutter(sannyasin, asList("one", "more than one", "two", "three"));
		assertThat(result, hasSize(2));
	}

}