package guru.bonacci.oogway.entrance.security;

public class DummyDecoder implements EntranceDecoder {

	@Override
	public String decode(String encodedInput) {
		return encodedInput;
	}
}
