package guru.bonacci.oogway.shareddomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GemIdCarrier extends GemCarrier {

	private String id;

	@Builder
	public GemIdCarrier(String id, String saying, String author){
		super(saying, author);
	    this.id = id;
	}
}
