package guru.bonacci.oogway.sannyas.filters;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.SannyasTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestApplication.class)
public class ProfanityFilterTest {

	@Autowired
	ProfanityFilter filter;

	@Test
	public void shouldGiveOneOfTheExpectedAnswers() {
		assertThat(true, is(not(filter.test("word1"))));
		assertThat(true, is(filter.test("aa")));
	}

}
