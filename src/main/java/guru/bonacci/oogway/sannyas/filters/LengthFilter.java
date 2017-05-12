package guru.bonacci.oogway.sannyas.filters;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

/**
 * Puts a limit on the length of the quotes to be indexed
 */
@Component
public class LengthFilter implements Predicate<String> {

	private static final Integer MAX_LENGTH = 1000;
	
	@Override
	public boolean test(String input) {
		return input.length() < MAX_LENGTH;
	}
}
