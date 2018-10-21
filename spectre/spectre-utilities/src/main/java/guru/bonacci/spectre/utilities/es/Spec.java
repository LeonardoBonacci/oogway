package guru.bonacci.spectre.utilities.es;

import guru.bonacci.oogway.relastic.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Spec extends BaseObject {

	private String message;

	private Geoip geoip = new Geoip();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Geoip {

		private Double latitude;

		private Double longitude;
	}
}
