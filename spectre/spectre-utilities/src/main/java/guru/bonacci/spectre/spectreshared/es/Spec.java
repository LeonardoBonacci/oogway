package guru.bonacci.spectre.spectreshared.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spec {

	public static final String MESSAGE = "message";

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
