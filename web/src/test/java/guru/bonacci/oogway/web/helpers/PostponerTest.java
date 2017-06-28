package guru.bonacci.oogway.web.helpers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, properties = {
	"file.name.answers.to.win.time:answers-to-win-time-test.txt" 
})
public class PostponerTest {

	@Autowired
	Postponer postponer;
	
	@Test
	public void shouldGiveOneOfTheExpectedAnswers() {
		List<String> answers = asList("answer one", "answer two", "answer three");
		for (int i=0; i<10; i++) 
			assertThat(answers, hasItem(postponer.saySomething()));
	}
}
