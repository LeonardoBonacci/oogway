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

	@Id
	private String id;

	@Field(type = String, store = true, analyzer = "english")
    private String essence;

	public Jewel() {
	}

	public Jewel(String essence) {
		this.essence = essence;
	}

	public Jewel(String id, String essence) {
		this.id = id;
		this.essence = essence;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getEssence() {
		return essence;
	}

	public void setEssence(String essence) {
		this.essence = essence;
	}
}
