package guru.bonacci.oogway.doorway.cheaters;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"file.name.answers.to.win.time=answers-to-win-time-test.txt"})
public class PostponerTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public Postponer postponer() {
    		return new Postponer();
        }
    }

	@Autowired
	Postponer postponer;

	@Test
	public void shouldGiveOneOfTheExpectedAnswers() {
		List<String> answers = asList("answer one", "answer two", "answer three");
		for (int i=0; i<10; i++) 
			assertThat(answers, hasItem(postponer.saySomething()));
	}
}
