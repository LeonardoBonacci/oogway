package guru.bonacci.oogway.lumberjack.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogRepositoryTests {

	@Autowired
	private VisitRepository repository;

	@Test
	public void shouldFindLogByName() {

		Visit stub = getStub();
		repository.save(stub);

		Visit found = repository.findByName(stub.getName());
		assertEquals(stub.getApiKey(), found.getApiKey());
	}

	private Visit getStub() {
		Visit visit = new Visit();
		visit.setName("test");
		visit.setApiKey("123");

		return visit;
	}
}
