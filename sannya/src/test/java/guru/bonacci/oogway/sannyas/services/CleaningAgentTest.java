package guru.bonacci.oogway.sannyas.services;

import static org.mockito.Matchers.contains;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.Gem;
import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.SannyasTestConfig;
import guru.bonacci.oogway.sannyas.filters.ProfanityFilter;
import guru.bonacci.oogway.sannyas.goodreads.GoodReadsSeeker;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class CleaningAgentTest {

	@Autowired
	CleaningAgent agent;

	@MockBean 
	ProfanityFilter profanityFilter;

	@MockBean 
	GoodReadsSeeker sannyasin;

	@MockBean //TODO remove
	GemRepository gemRepo;

	@Test
    public void shouldCleanSimple() {
		when(sannyasin.postfilteringStep()).thenReturn(asList(i -> true));
		when(profanityFilter.test("one")).thenReturn(true);

		List<Gem> result = agent.noMoreClutter(sannyasin, asList("one" ,"two", "three"));
		assertThat(result, hasSize(1));
		assertThat(result.get(0).getEssence(), is(equalTo("one")));
	}
	
	@Test
    public void shouldCleanComplex() {
		when(sannyasin.postfilteringStep()).thenReturn(asList(i -> i.equals("one") || i.equals("more than one") || i.equals("two"), i -> true));
		when(profanityFilter.test(contains("one"))).thenReturn(true);

		List<Gem> result = agent.noMoreClutter(sannyasin, asList("one", "more than one", "two", "three"));
		assertThat(result, hasSize(2));
	}

}
