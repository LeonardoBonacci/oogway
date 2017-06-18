package guru.bonacci.oogway.sannyas.services;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static org.apache.commons.lang.StringUtils.reverse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.SannyasTestConfig;
import guru.bonacci.oogway.sannyas.goodreads.GoodReadsSeeker;
import guru.bonacci.oogway.sannyas.services.ForePlayer;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class ForePlayerTest {

	private static final String INPUT = "some string without meaning";

	@Autowired
	ForePlayer forePlayer;

	@MockBean
	GoodReadsSeeker sannyasin;
	
	@MockBean
	DuplicateRemover duplicateRemover;

	@MockBean //TODO remove
	GemRepository gemRepo;

	@Test
    public void shouldGoForItSimple() {
		when(sannyasin.preprocessingSteps()).thenReturn(asList(identity()));
		when(duplicateRemover.apply(INPUT)).thenReturn(INPUT);

		String preprocessedInput = forePlayer.play(sannyasin, INPUT);
		assertThat(preprocessedInput, is(equalTo(INPUT)));
	}

	@Ignore //TODO something's wrong with the duplicateRemover
	@Test
    public void shouldGoForItComplicated() {
		String inputReverse = "gninaem tuohtiw gnirts emos";
		String somethingElse = "something else";
		
		Function<String,String> f = str -> reverse(str);
		when(sannyasin.preprocessingSteps()).thenReturn(asList(f));
		when(duplicateRemover.apply(inputReverse)).thenReturn(somethingElse);

		String preprocessedInput = forePlayer.play(sannyasin, INPUT);
		assertThat(preprocessedInput, is(equalTo(somethingElse)));
	}
}
