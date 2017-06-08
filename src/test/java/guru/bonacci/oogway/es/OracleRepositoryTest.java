package guru.bonacci.oogway.es;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.TestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OracleRepositoryTest {

	@Autowired
	private OracleRepository repository;

	@Before
	public void emptyData() {
		repository.deleteAll();
	}

	@Test
	public void shouldIndexSingleBookEntity() {
		repository.save(new Gem("aa"));
//		// lets try to search same record in elasticsearch
//		Book indexedBook = repository.findOne(book.getId());
//		assertThat(indexedBook, is(notNullValue()));
//		assertThat(indexedBook.getId(), is(book.getId()));
	}
}
