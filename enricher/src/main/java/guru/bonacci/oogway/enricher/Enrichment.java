package guru.bonacci.oogway.enricher;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrichment implements Serializable {

	private static final long serialVersionUID = -2926410041404429201L;

	private String author, comment;
}
