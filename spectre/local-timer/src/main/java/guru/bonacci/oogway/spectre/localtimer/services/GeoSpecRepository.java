package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoSpecRepository extends ElasticsearchRepository<GeoSpec, String> {
}
