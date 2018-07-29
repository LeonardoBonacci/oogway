package guru.bonacci.intercapere.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InterCapereApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InterCapereApp.class)
				.run(args);
	}

}
