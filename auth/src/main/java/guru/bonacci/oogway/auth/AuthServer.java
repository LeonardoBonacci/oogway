package guru.bonacci.oogway.auth;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import guru.bonacci.oogway.auth.models.User;
import guru.bonacci.oogway.auth.services.CustomUserService;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableResourceServer
public class AuthServer {

	private final Logger logger = getLogger(this.getClass());

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
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
	CommandLineRunner init(CustomUserService userService) {
		return (evt) -> Arrays.asList("oogway,user1,app".split(",")).forEach(username -> {
			User user = new User();
			user.setUsername(username);
			user.setPassword("password");
			user.setApiKey(StringUtils.reverse(username));
			userService.registerUser(user);

			logger.info("User added: " + user);
		});
	}
}
