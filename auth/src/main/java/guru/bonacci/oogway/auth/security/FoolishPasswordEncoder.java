package guru.bonacci.oogway.auth.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FoolishPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence seq) {
		return seq.toString();
	}

	@Override
	public boolean matches(CharSequence seq, String s) {
		return seq.toString().equals(s);
	}
	
	// Lama? Kacha!
	public String decode(String s) {
		return s;
	}
}
