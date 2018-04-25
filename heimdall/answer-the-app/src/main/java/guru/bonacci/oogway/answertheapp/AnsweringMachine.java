package guru.bonacci.oogway.answertheapp;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static guru.bonacci.oogway.utilities.CustomListUtils.random;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@RestController
@SpringBootApplication
public class AnsweringMachine {

	private final Logger logger = getLogger(this.getClass());

	List<GemCarrier> friedrichsBest;

	public static void main(String[] args) {
		SpringApplication.run(AnsweringMachine.class, args);
	}

	@PostConstruct
	public void postEventum() {
		try {
			friedrichsBest = readToList("nietzsche.txt").stream()
					.map(quote -> new GemCarrier(quote, "Friedrich Nietzsche")).collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("Where is Nietzsche??", e);
		}
	}

	@GetMapping("/greeting")
	public GemCarrier greeting() {
		return random(friedrichsBest).get(); // you may crash..
	}
}