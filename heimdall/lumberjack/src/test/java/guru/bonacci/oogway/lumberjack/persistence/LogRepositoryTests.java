package guru.bonacci.oogway.lumberjack.persistence;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.Before;
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

	Visit visit1, visit2;

	@Before
	public void init() {
		repository.deleteAll();
		
		visit1 = new Visit();
		visit1.setName("testing");
		visit1.setApiKey("123");
		visit1.setMoment(Instant.now());
		repository.save(visit1);
		
		visit2 = new Visit();
		visit2.setName("jamming");
		visit2.setApiKey("456");
		visit2.setMoment(Instant.now());
		repository.save(visit2);
	}
	
	@Test
	public void shouldFindLogByName() {
		Visit found = repository.findByName(visit1.getName());
		assertEquals(visit1.getApiKey(), found.getApiKey());
		assertEquals(visit1.getMoment(), found.getMoment());
	}

	@Test
	public void shouldFindLogsByMomentRange() {
		Instant now = Instant.now();
		List<Visit> found = repository.findByMomentBetween(now.minusSeconds(10), now.plusSeconds(10));
		assertThat(found.size(), is(2));
	}

	@Test
	public void shouldCountLogsByMomentRange() {
		Instant now = Instant.now();
		long nrFound = repository.countByMomentBetween(now.minusSeconds(10), now.plusSeconds(10));
		assertThat(nrFound, is(2L));
	}
}
