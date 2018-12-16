package guru.bonacci.oogway.profanity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableBinding(Binding.class)
@SpringBootApplication
public class ProfanityApp {

	public static void main(String[] args) {
		SpringApplication.run(ProfanityApp.class, args);
	}
}
