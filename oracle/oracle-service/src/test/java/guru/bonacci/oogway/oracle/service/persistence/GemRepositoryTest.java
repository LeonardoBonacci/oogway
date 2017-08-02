package guru.bonacci.oogway.oracle.service.persistence;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
//A little hack to avoid creating profiles at this moment :)
//The test resource property overrides some of the oracle.properties that is
//read by the default configuration OracleConfig
@TestPropertySource("classpath:oracle-test.properties")
public class GemRepositoryTest {
	
	@Autowired
	GemRepository repo;
	
	@MockBean
	JmsTemplate jms;

	@Before
	public void setup() {
		repo.deleteAll();
	}

	@Test
	public void shouldSaveAllFields1() {
		String said = "the said";
		String by = "the by";
		LocalDateTime creation = LocalDateTime.now();
		Gem input = new Gem(said, by);
		input.setCreation(creation);
		repo.save(input);

		Gem result = repo.findOne(input.getId());

		assertThat(result.getSaid(), is(equalTo(said)));
		assertThat(result.getBy(), is(equalTo(by)));
		//TODO write timeobjectmapper for time conversion
		//assertThat(result.getCreation(), is(equalTo(creation)));
	}

	@Test
	public void shouldSaveAllFields2() {
		String said = "the said";
		String by = "the by";
		Gem g = new Gem(said, by);
		repo.saveTheNewOnly(g);

		Gem result = repo.consultTheOracle("the said").get();

		assertThat(result.getSaid(), is(equalTo(said)));
		assertThat(result.getBy(), is(equalTo(by)));
		//TODO write timeobjectmapper for time conversion
		//assertThat(result.getCreation(), is(equalTo(creation)));
	}

	@Test
	public void shouldSaveAUnique() {
		assertThat(repo.count(), is(equalTo(0L)));

		repo.saveTheNewOnly(new Gem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Test
	public void shouldNotSaveAnExisting() {
		repo.saveTheNewOnly(new Gem("a"));
		repo.saveTheNewOnly(new Gem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Test
	public void shouldSaveTheNewOnly() {
		repo.saveTheNewOnly(new Gem("a"));
		repo.saveTheNewOnly(new Gem("a"), new Gem("b"));
		assertThat(repo.count(), is(equalTo(2L)));
	}

	@Test
	public void shouldFindSimilarGem() {
		Gem gem = new Gem("how are you I am fine");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("hello how are you");
		assertThat(gem, is(equalTo(result.get())));
	}

	@Test
	public void shouldFindSimilarGemMultipleTimes() {
		Gem gem1 = new Gem("how are you I am fine");
		Gem gem2 = new Gem("how are you I am not fine");
		repo.save(asList(gem1, gem2));
		
		Set<Gem> results = new HashSet<>();
		for (int i=0; i<10; i++) 
			results.add(repo.consultTheOracle("hello how are you").get());

		assertThat(results.size(), greaterThan(1));
	}

	@Test
	public void shouldNotFindDifferentGem() {
		Gem gem = new Gem("how are you I am fine");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("something completely different");
		assertThat(true, is(not(result.isPresent())));
	}
}
