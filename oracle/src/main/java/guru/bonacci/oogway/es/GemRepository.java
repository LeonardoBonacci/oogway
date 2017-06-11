package guru.bonacci.oogway.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemRepository extends ElasticsearchRepository<Gem,String>, GemRepositoryCustom {
}