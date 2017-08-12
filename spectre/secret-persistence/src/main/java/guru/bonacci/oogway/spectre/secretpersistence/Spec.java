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

	// even too lazy for getters and setters today...
	@Id
	public String id;

	@Field(type = FieldType.Nested)
	public Geoip geoip;

	// Define what you need...
	public class Geoip {

		public Geoip() {}

		public Geoip(Double lat, Double lon) {
			latitude = lat;
			longitude = lon;
		}
		
		@Field(type = FieldType.Double)
		public Double latitude;

		@Field(type = FieldType.Double)
		public Double longitude;
	}

}
