package guru.bonacci.oogway.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootApplication.class)
@TestPropertySource("classpath:/application-test.properties")
public class OracleRepoTest {

	@Autowired
	OracleRepo repo;
	
	@Test
	public void shouldGo() {
		Gem g = new Gem("a");
		repo.save(g);
	}
}
