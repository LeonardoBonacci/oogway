package guru.bonacci.oogway.enricher;

import java.io.Serializable;

import guru.bonacci.oogway.domain.EnquiryEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BigGem implements Serializable {

	private static final long serialVersionUID = -2926410041404429201L;

	private EnquiryEvent e1;
	private Enrichment e2;
}
