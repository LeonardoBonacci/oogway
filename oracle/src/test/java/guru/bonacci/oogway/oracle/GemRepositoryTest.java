package guru.bonacci.oogway.oracle;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootApplication.class)
@TestPropertySource("classpath:/application-test.properties")
public class GemRepositoryTest {

	@Autowired
	GemRepository repo;
	
	@Before
	public void setup() {
		repo.deleteAll();
	}
	
	@Test
	public void shouldSaveTheNewOnly() {
		assertThat(repo.count(), is(equalTo(0L)));

		Gem g1 = new Gem("a");
		repo.saveTheNewOnly(singletonList(g1));
		assertThat(repo.count(), is(equalTo(1L)));
		
		Gem g2 = new Gem("a");
		repo.saveTheNewOnly(singletonList(g2));
		assertThat(repo.count(), is(equalTo(1L)));

		Gem g3 = new Gem("3");
		repo.saveTheNewOnly(asList(g2, g3));
		assertThat(repo.count(), is(equalTo(2L)));
	}
}
