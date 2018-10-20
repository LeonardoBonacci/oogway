package guru.bonacci.spectre.utilities.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.stereotype.Repository;

import guru.bonacci.oogway.relastic.ElasticAdapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class SpectreRepository {

	public static final String INDEX = "logstash-spectre";
	public static final String TYPE = "logs";
	
    private final ElasticAdapter<Spec> adapter;

	public SpectreRepository(ElasticAdapter<Spec> adapter) {
    	this.adapter = adapter.theIndex(INDEX).theType(TYPE).theGenericType(Spec.class);
    }


	public Mono<Spec> findById(String id) {
		return adapter.findById(id);
    }

    public Mono<UpdateResponse> update(String specId, String field, Object nestedObject) {
        try {
        	XContentBuilder json = jsonBuilder().startObject().field(field, nestedObject).endObject();
	        return adapter.updateDoc(specId, json)
	        			  .doOnError(e -> log.error("Unable to update {}", specId, e));
		} catch (IOException e) {
			e.printStackTrace();
			return Mono.empty();
		}
    }
}
