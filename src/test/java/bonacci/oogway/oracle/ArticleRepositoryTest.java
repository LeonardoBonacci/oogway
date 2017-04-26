package bonacci.oogway.oracle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springContext-test.xml")
public class ArticleRepositoryTest {

    public static final String RIZWAN_IDREES = "Rizwan Idrees";
    public static final String MOHSIN_HUSEN = "Mohsin Husen";
    public static final String JONATHAN_YAN = "Jonathan Yan";
    public static final String ARTUR_KONCZAK = "Artur Konczak";
    public static final int YEAR_2002 = 2002;
    public static final int YEAR_2001 = 2001;
    public static final int YEAR_2000 = 2000;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Article.class);
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
        elasticsearchTemplate.refresh(Article.class);

        IndexQuery article1 = new ArticleBuilder("1").title("article four").buildIndex();
        IndexQuery article2 = new ArticleBuilder("2").title("article three").buildIndex();
        IndexQuery article3 = new ArticleBuilder("3").title("article two").buildIndex();
        IndexQuery article4 = new ArticleBuilder("4").title("article one").buildIndex();

        elasticsearchTemplate.index(article1);
        elasticsearchTemplate.index(article2);
        elasticsearchTemplate.index(article3);
        elasticsearchTemplate.index(article4);
        elasticsearchTemplate.refresh(Article.class);
    }

    @Test
    public void shouldReturnSomething() {
//
//        String facetName = "fauthors";
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").build()).build();
//        // when
//        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
//        // then
//        assertThat(result.getNumberOfElements(), is(equalTo(4)));
//
//        TermResult facet = (TermResult) result.getFacet(facetName);
//        Term term = facet.getTerms().get(0);
//        assertThat(term.getTerm(), is(RIZWAN_IDREES));
//        assertThat(term.getCount(), is(4));
//
//        term = facet.getTerms().get(1);
//        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
//        assertThat(term.getCount(), is(3));
//
//        term = facet.getTerms().get(2);
//        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
//        assertThat(term.getCount(), is(2));
//
//        term = facet.getTerms().get(3);
//        assertThat(term.getTerm(), is(JONATHAN_YAN));
//        assertThat(term.getCount(), is(1));
    }
}

