package guru.bonacci.spectre.utilities;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.relastic.ElasticConfig;

@Configuration
@ComponentScan
@Import(ElasticConfig.class)
public class SpectreConfig {

}
