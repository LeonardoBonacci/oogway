package guru.bonacci.oogway.entrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import guru.bonacci.oogway.entrance.security.TestDecryptor;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { EntranceServer.class }))
public class EntranceTestApp {

	@Bean
	public TestDecryptor decoder() {
		return new TestDecryptor(); 
	}

	public static void main(String[] args) {
		SpringApplication.run(EntranceTestApp.class, args);
	}
}