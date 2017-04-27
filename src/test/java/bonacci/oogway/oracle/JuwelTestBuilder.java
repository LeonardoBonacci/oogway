package bonacci.oogway.oracle;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

public class JuwelTestBuilder {

    private Juwel juwel;

    public JuwelTestBuilder(String id) {
        juwel = new Juwel();
        juwel.setId(id);
    }

    public JuwelTestBuilder essence(String essence) {
        juwel.setEssence(essence);
        return this;
    }

    public Juwel build() {
        return juwel;
    }

    public IndexQuery buildIndex() {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(juwel.getId());
        indexQuery.setObject(juwel);
        return indexQuery;
    }
}
