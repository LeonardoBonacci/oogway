package guru.bonacci.oogway.doorway.oracle;

import guru.bonacci.oogway.shareddomain.GemCarrier;

public interface Oracle {

	GemCarrier enquire(String q, String apiKey);
}
