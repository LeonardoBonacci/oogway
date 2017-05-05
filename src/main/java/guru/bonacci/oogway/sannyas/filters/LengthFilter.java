package guru.bonacci.oogway.sannyas.filters;

import org.springframework.stereotype.Component;

@Component
public class LengthFilter implements PostProcesFilter {

	private static final Integer MAX_LENGTH = 200;
	
	@Override
	public boolean test(String input) {
		return input.length() < MAX_LENGTH;
	}
}
