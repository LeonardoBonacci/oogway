package guru.bonacci.oogway.sannya.service.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannya.service.steps.DuplicateRemover;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class DuplicateRemoverTest {

	@Autowired
	DuplicateRemover duplicateRemover;

	@Test
	public void shouldRemoveDuplicates() {
		String withDuplicates = "hello hello I am going home hello home";
		String withoutDuplicates = "hello I am going home";
		assertThat(duplicateRemover.apply(withDuplicates), is(equalTo(withoutDuplicates)));
	}
}
