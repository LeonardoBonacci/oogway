package bonacci.oogway.oracle;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

public class ArticleBuilder {

    private Article result;

    public ArticleBuilder(String id) {
        result = new Article(id);
    }

    public ArticleBuilder title(String title) {
        result.setTitle(title);
        return this;
    }

    public Article build() {
        return result;
    }

    public IndexQuery buildIndex() {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(result.getId());
        indexQuery.setObject(result);
        return indexQuery;
    }
}
