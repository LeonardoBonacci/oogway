package bonacci.oogway.oracle;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bonacci.oogway.web.ARepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springContext-test.xml")
public class SampleJuwelRepositoryTest {

    @Resource
    private ARepository sampleJuwelRepository;

    @Before
    public void emptyData(){
        sampleJuwelRepository.deleteAll();
    }

    @Test
    public void shouldIndexSingleBookEntity(){
        Juwel juwel = new Juwel("123455", "Spring Data Elasticsearch Test");
        //Indexing using sampleJuwelRepository
        sampleJuwelRepository.save(juwel);
        //lets try to search same record in elasticsearch
        Juwel indexedArticle = sampleJuwelRepository.findOne(juwel.getId());
        assertThat(indexedArticle,is(notNullValue()));
        assertThat(indexedArticle.getId(),is(juwel.getId()));
    }
}
