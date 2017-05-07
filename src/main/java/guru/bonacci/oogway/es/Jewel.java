package guru.bonacci.oogway.es;

import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * A jewel is a cut and polished precious stone
 */
@Document(indexName = "oracle", type = "quote", shards = 1, replicas = 0, refreshInterval = "-1")
public class Jewel {

	public static final String ESSENCE = "essence";

	@Id // elasticsearch only knows the concept of uniqueness on the key-field, and we want unique quotes..
	@Field(type = String, store = true, analyzer = "english", searchAnalyzer = "english")
    private String essence;

	public Jewel() {
	}

	public Jewel(String essence) {
		this.essence = essence;
	}

	public String getEssence() {
		return essence;
	}

	public void setEssence(String essence) {
		this.essence = essence;
	}
}
