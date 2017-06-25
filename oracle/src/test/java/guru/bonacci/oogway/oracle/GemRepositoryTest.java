package guru.bonacci.oogway.oracle;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.persistence.PersistedGem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class GemRepositoryTest {

	@Autowired
	GemRepository repo;
	
	@Before
	public void setup() {
		repo.deleteAll();
	}

	@Test
	public void shouldSaveAUnique() {
		assertThat(repo.count(), is(equalTo(0L)));

		repo.saveTheNewOnly(new PersistedGem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Test
	public void shouldNotSaveAnExisting() {
		repo.saveTheNewOnly(new PersistedGem("a"));
		repo.saveTheNewOnly(new PersistedGem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Test
	public void shouldSaveTheNewOnly() {
		repo.saveTheNewOnly(new PersistedGem("a"));
		repo.saveTheNewOnly(new PersistedGem("a"), new PersistedGem("b"));
		assertThat(repo.count(), is(equalTo(2L)));
	}

	@Test
	public void shouldFindSimilarGem() {
		PersistedGem persistedGem = new PersistedGem("how are you I am fine");
		repo.save(persistedGem);
		
		Optional<PersistedGem> result = repo.consultTheOracle("hello how are you");
		assertThat(persistedGem, is(equalTo(result.get())));
	}

	@Test
	public void shouldFindSimilarGemMultipleTimes() {
		PersistedGem gem1 = new PersistedGem("how are you I am fine");
		PersistedGem gem2 = new PersistedGem("how are you I am not fine");
		repo.save(asList(gem1, gem2));
		
		Set<PersistedGem> results = new HashSet<>();
		for (int i=0; i<10; i++) 
			results.add(repo.consultTheOracle("hello how are you").get());

		assertThat(results.size(), greaterThan(1));
	}

	@Test
	public void shouldNotFindDifferentGem() {
		PersistedGem persistedGem = new PersistedGem("how are you I am fine");
		repo.save(persistedGem);
		
		Optional<PersistedGem> result = repo.consultTheOracle("something completely different");
		assertThat(true, is(not(result.isPresent())));
	}
}
