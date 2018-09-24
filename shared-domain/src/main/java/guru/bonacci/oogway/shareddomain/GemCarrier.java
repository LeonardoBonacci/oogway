package guru.bonacci.oogway.shareddomain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * https://rlogiacco.wordpress.com/2010/05/25/five-reasons-to-hate-dtos/
 * I personally don't hate DTO's, but I also don't like them.
 * Therefore an IDTO: Incognito-DTO.
 */
@Data
@NoArgsConstructor
public class GemCarrier {

	@NonNull
	private String saying;

	private String author;

	public GemCarrier(String saying) {
		this.saying = saying;
	}

	public GemCarrier(String saying, String author) {
		this(saying);
		this.author = author;
	}
}
