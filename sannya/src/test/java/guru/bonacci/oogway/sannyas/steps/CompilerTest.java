package guru.bonacci.oogway.sannyas.steps;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.reverse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import guru.bonacci.oogway.TestConfig;
import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.sannyas.services.ForePlayer;

@ContextConfiguration(classes = TestConfig.class)
public class CompilerTest {

	private static final String INPUT = "some string without meaning";

	@InjectMocks
	@Autowired
	private ForePlayer forePlayer;

	@Mock
	private Sannyasin sannya;
	
	@Mock
	private DuplicateRemover duplicateRemover;

	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void shouldGoForItSimple() {
		when(sannya.preprocessingSteps()).thenReturn(asList(Function.identity()));
		when(duplicateRemover.apply(INPUT)).thenReturn(INPUT);

		String preprocessedInput = forePlayer.puzzle(sannya, INPUT);
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

		String preprocessedInput = forePlayer.puzzle(sannya, INPUT);
		assertThat(preprocessedInput, is(equalTo(somethingElse)));
	}
}
