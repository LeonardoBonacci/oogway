package bonacci.oogway.services.web;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import bonacci.oogway.es.Juwel;

@Repository
public interface ARepository extends ElasticsearchRepository<Juwel,String> {
}
