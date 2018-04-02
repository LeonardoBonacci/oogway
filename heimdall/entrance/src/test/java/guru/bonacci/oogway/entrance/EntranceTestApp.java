package guru.bonacci.oogway.entrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import guru.bonacci.oogway.entrance.EntranceServer;

import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { EntranceServer.class }))
public class EntranceTestApp {

	public static void main(String[] args) {
		SpringApplication.run(EntranceTestApp.class, args);
	}
}