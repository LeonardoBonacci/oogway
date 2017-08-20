package guru.bonacci.oogway.spectre.secretpersistence;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan
@EnableElasticsearchRepositories
public class SecretPersistenceTestConfig {
}
