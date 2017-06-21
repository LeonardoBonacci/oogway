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

import guru.bonacci.oogway.sannyas.SannyasTestApplication;
import guru.bonacci.oogway.sannyas.goodreads.GoodReadsSeeker;
import guru.bonacci.oogway.sannyas.steps.DuplicateRemover;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestApplication.class)
public class ForePlayerTest {

	private static final String INPUT = "some string without meaning";

	@Autowired
	ForePlayer player;

	@MockBean
	GoodReadsSeeker sannyasin;
	
	@MockBean
	DuplicateRemover duplicateRemover;

	@Test
    public void shouldGoForItSimple() {
		when(sannyasin.preprocessingSteps()).thenReturn(asList(identity()));
		when(duplicateRemover.apply(INPUT)).thenReturn(INPUT);

		String preprocessedInput = player.play(sannyasin, INPUT);
		assertThat(preprocessedInput, is(equalTo(INPUT)));
	}

	@Test
    public void shouldGoForItComplicated() {
		String inputReverse = "gninaem tuohtiw gnirts emos";
		String somethingElse = "something else";
		
		Function<String,String> f = str -> reverse(str);
		when(sannyasin.preprocessingSteps()).thenReturn(asList(f, f, f));
		when(duplicateRemover.apply(inputReverse)).thenReturn(somethingElse);

		String preprocessedInput = player.play(sannyasin, INPUT);
		System.out.println(preprocessedInput);
		assertThat(preprocessedInput, is(equalTo(somethingElse)));
	}
}
