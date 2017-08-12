package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * To retrieve some geo data from ES
 */
public class GeoSpec extends Spec {

	@Field(type = FieldType.Nested)
	private Geoip geoip;

	public Geoip getGeoip() {
		return geoip;
	}

	public void setGeoip(Geoip geoip) {
		this.geoip = geoip;
	}
	
	// Take what you need...
	public class Geoip {

		@Field(type = FieldType.Double)
		public Double latitude;

		@Field(type = FieldType.Double)
		public Double longitude;
	}

}
