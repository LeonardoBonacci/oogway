package guru.bonacci.oogway.spectre.secretpersistence;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:secret-persistence.properties")
public class SecretPersistenceConfig {
}
