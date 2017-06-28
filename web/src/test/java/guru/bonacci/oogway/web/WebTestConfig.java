package guru.bonacci.oogway.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:web-test.properties")
@TestPropertySource(properties = {
	"file.name.answers.to.win.time:testpropertysources-loaded"
})
public class WebTestConfig {

	@Bean
	public String just() {
		return "";
	}
}
