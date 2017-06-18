package guru.bonacci.oogway.sannyas.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.SannyasTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class DuplicateRemoverTest {

	@Autowired
	DuplicateRemover duplicateRemover;

	@MockBean //TODO remove
	GemRepository gemRepo;

	@Test
	public void shouldRemoveDuplicates() {
		String withDuplicates = "hello hello I am going home hello home";
		String withoutDuplicates = "hello I am going home";
		assertThat(duplicateRemover.apply(withDuplicates), is(equalTo(withoutDuplicates)));
	}
}
