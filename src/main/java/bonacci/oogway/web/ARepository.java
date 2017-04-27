package bonacci.oogway.web;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import bonacci.oogway.oracle.Juwel;

@Repository
public interface ARepository extends ElasticsearchRepository<Juwel,String> {
}
