package guru.bonacci.oogway.spectre.secretpersistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * To retrieve some spectre data from ES
 * Define what you need...
 */
@Document(indexName = "logstash-spectre", type = "logs", shards = 1, replicas = 0, refreshInterval = "-1")
public class Spec {

	@Id
	private String id;

	public Spec() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Field(type = FieldType.Nested)
	private Geoip geoip;

	public Geoip getGeoip() {
		return geoip;
	}

	public void setGeoip(Geoip geoip) {
		this.geoip = geoip;
	}
	
	// Define what you need...
	public class Geoip {

		@Field(type = FieldType.Double)
		public Double latitude;

		@Field(type = FieldType.Double)
		public Double longitude;
	}

}
