package guru.bonacci.oogway.sannyas.steps;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.reverse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.SannyasTestConfig;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.services.ForePlayer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class ForePlayerTest {

	private static final String INPUT = "some string without meaning";

	@Autowired
	ForePlayer forePlayer;

	@Mock
	Sannyasin sannya;
	
	@Mock
	DuplicateRemover duplicateRemover;

	@Test
    public void shouldGoForItSimple() {
		when(sannya.preprocessingSteps()).thenReturn(asList(Function.identity()));
		when(duplicateRemover.apply(INPUT)).thenReturn(INPUT);

		String preprocessedInput = forePlayer.play(sannya, INPUT);
		assertThat(preprocessedInput, is(equalTo(INPUT)));
	}
	
	@Ignore
	@Test
    public void shouldGoForItComplicated() {
		String inputReverse = "gninaem tuohtiw gnirts emos";
		String somethingElse = "something else";
		
		Function<String,String> f = str -> reverse(str);
		when(sannya.preprocessingSteps()).thenReturn(asList(f));
		when(duplicateRemover.apply(inputReverse)).thenReturn(somethingElse);

		String preprocessedInput = forePlayer.play(sannya, INPUT);
		assertThat(preprocessedInput, is(equalTo(somethingElse)));
	}
}
