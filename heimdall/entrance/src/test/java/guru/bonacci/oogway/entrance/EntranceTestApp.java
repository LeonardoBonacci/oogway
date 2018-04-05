package guru.bonacci.oogway.entrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import guru.bonacci.oogway.entrance.security.DummyDecoder;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { EntranceServer.class }))
public class EntranceTestApp {

	@Bean
	public DummyDecoder decoder() {
		return new DummyDecoder(); 
	}

	public static void main(String[] args) {
		SpringApplication.run(EntranceTestApp.class, args);
	}
}