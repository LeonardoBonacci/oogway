package guru.bonacci.oogway.enricher.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BigGem implements Serializable {

	private static final long serialVersionUID = -2926410041404429201L;

	public BigGem(String saying, String author, Enrichment data) {
		this.saying = saying;
		this.author = author;
		this.extra = data;
	}
	
	private String saying, author;
	private Enrichment extra;
}
