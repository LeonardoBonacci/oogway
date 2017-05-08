package guru.bonacci.oogway.sannyas.filters;

import org.springframework.stereotype.Component;

/**
 * Puts a limit on the length of the quotes to be indexed
 */
@Component
public class LengthFilter implements PostProcesFilter {

	private static final Integer MAX_LENGTH = 1000;
	
	@Override
	public boolean test(String input) {
		return input.length() < MAX_LENGTH;
	}
}
