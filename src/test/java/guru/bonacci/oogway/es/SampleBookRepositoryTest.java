package guru.bonacci.oogway.es;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/please.xml")
public class SampleBookRepositoryTest {

    @Resource
    private OracleRepository repository;

    @Resource
	private ElasticsearchTemplate template;

	@Before
    public void emptyData(){
        repository.deleteAll();
    }

    @Test
    public void shouldIndexSingleBookEntity(){

//        Book book = new Book();
//        book.setId("123455");
//        book.setName("Spring Data Elasticsearch");
//        book.setVersion(System.currentTimeMillis());
//        repository.save(book);
//        //lets try to search same record in elasticsearch
//        Book indexedBook = repository.findOne(book.getId());
//        assertThat(indexedBook,is(notNullValue()));
//        assertThat(indexedBook.getId(),is(book.getId()));
    }
}
