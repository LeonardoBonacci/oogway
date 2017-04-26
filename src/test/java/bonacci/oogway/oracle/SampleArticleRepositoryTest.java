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
public class SampleArticleRepositoryTest {

    @Resource
    private ARepository sampleArticleRepository;

    @Before
    public void emptyData(){
        sampleArticleRepository.deleteAll();
    }

    @Test
    public void shouldIndexSingleBookEntity(){
        Article article = new Article();
        article.setId("123455");
        article.setTitle("Spring Data Elasticsearch Test Article");
        //Indexing using sampleArticleRepository
        sampleArticleRepository.save(article);
        //lets try to search same record in elasticsearch
        Article indexedArticle = sampleArticleRepository.findOne(article.getId());
        assertThat(indexedArticle,is(notNullValue()));
        assertThat(indexedArticle.getId(),is(article.getId()));
    }
}
