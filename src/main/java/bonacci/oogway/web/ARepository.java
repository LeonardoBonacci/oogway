package bonacci.oogway.web;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import bonacci.oogway.oracle.Article;

@Repository
public interface ARepository extends ElasticsearchRepository<Article,String> {
}
