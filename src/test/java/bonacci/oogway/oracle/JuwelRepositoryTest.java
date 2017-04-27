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
public class JuwelRepositoryTest {

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
        elasticsearchTemplate.deleteIndex(Juwel.class);
        elasticsearchTemplate.createIndex(Juwel.class);
        elasticsearchTemplate.putMapping(Juwel.class);
        elasticsearchTemplate.refresh(Juwel.class);

        IndexQuery juwel1 = new JuwelTestBuilder("1").essence("juwel four").buildIndex();
        IndexQuery juwel2 = new JuwelTestBuilder("2").essence("juwel three").buildIndex();
        IndexQuery juwel3 = new JuwelTestBuilder("3").essence("juwel two").buildIndex();
        IndexQuery juwel4 = new JuwelTestBuilder("4").essence("juwel one").buildIndex();

        elasticsearchTemplate.index(juwel1);
        elasticsearchTemplate.index(juwel2);
        elasticsearchTemplate.index(juwel3);
        elasticsearchTemplate.index(juwel4);
        elasticsearchTemplate.refresh(Juwel.class);
    }

    @Test
    public void shouldReturnSomething() {
//
//        String facetName = "fauthors";
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").build()).build();
//        // when
//        FacetedPage<juwel> result = elasticsearchTemplate.queryForPage(searchQuery, juwel.class);
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

