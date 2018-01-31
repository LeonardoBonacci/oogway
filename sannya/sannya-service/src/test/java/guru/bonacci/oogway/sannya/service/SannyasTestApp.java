package guru.bonacci.oogway.sannya.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = {SannyasServer.class}))
@PropertySource("classpath:sannyas-test.properties")
public class SannyasTestApp {

	public static void main(String[] args) {
		SpringApplication.run(SannyasTestApp.class, args);
	}
}