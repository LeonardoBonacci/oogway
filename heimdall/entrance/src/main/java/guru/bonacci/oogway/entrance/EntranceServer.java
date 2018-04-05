package guru.bonacci.oogway.entrance;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.entrance.events.EntranceEventChannels;
import guru.bonacci.oogway.entrance.security.RSADecoder;
import guru.bonacci.oogway.utils.security.RSAKeyHelper;

/**
 * Service for the user to communicate with
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(EntranceEventChannels.class)
@IntegrationComponentScan
public class EntranceServer {

	@Bean
	public RSADecoder decoder() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		return new RSADecoder(RSAKeyHelper.loadPrivateKey("/ubuntu1/")); //volume mount in Dockerfile
	}

	public static void main(String[] args) {
		SpringApplication.run(EntranceServer.class, args);
	}
}
