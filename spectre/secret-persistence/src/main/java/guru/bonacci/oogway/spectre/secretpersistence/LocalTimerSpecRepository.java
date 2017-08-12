package guru.bonacci.oogway.spectre.secretpersistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTimerSpecRepository extends ElasticsearchRepository<LocalTimerSpec, String> {
}
