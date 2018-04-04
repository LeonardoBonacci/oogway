package guru.bonacci.oogway.auth;

import static org.apache.commons.lang.StringUtils.reverse;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import guru.bonacci.oogway.auth.models.User;
import guru.bonacci.oogway.auth.security.RSAPasswordEncoder;
import guru.bonacci.oogway.auth.services.MyUserService;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@EnableAsync
public class AuthServer {

	private final Logger logger = getLogger(this.getClass());

	@Bean
	public RSAPasswordEncoder passwordEncoder() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyPair keyPair = RSAKeyHelper.loadKeyPair("/ubuntu1/"); //volume mount in dockerfile
		return new RSAPasswordEncoder(keyPair.getPublic());
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServer.class, args);
	}

	@Bean
	@Qualifier("mainDataSource")
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		return db;
	}

	@Bean
	CommandLineRunner init(MyUserService accountService) {
		return (evt) -> Arrays.asList("user1,user2".split(",")).forEach(username -> {
			User user = new User();
			user.setUsername(username);
			user.setPassword("password");
			user.setApiKey(reverse(user.getUsername()));
			accountService.registerUser(user);

			logger.info("User added: " + user);
		});
	}
}
