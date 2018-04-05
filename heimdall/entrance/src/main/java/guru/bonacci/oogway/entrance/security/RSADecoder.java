package guru.bonacci.oogway.entrance.security;

import java.security.PrivateKey;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSADecoder implements EntranceDecoder {

	private PrivateKey privateKey;

	public RSADecoder(PrivateKey key) {
    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.privateKey = key;
	}

	@Override
	public String decode(String encodedInput) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encodedInput)));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
