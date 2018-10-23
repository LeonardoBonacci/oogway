package guru.bonacci.oogway.doorway.bigbang;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigGem {

	private GemCarrier gem;
	private String bigbang;
}
