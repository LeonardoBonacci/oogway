package guru.bonacci.oogway.answertheapp;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static guru.bonacci.oogway.utilities.CustomListUtils.random;
import static org.slf4j.LoggerFactory.getLogger;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@RestController
@SpringBootApplication
public class AnsweringMachine {

	private final Logger logger = getLogger(this.getClass());

	List<GemCarrier> answers;

	public static void main(String[] args) {
		SpringApplication.run(AnsweringMachine.class, args);
	}

	@PostConstruct
	public void postEventum() {
		try {
			answers = readToList("nietzsche.txt").stream()
					.map(quote -> new GemCarrier(quote, "Friedrich Nietzsche")).collect(toList());
			answers.addAll(readToList("schop.txt").stream()
					.map(quote -> new GemCarrier(quote, "Arthur Schopenhauer")).collect(toList()));
		} catch (IOException e) {
			logger.error("What just happened??", e);
		}
	}

	@GetMapping("/consult")
	public GemCarrier greeting(@RequestParam("q") String q, @RequestParam("apikey") String apiKey) {
		logger.info("incoming: " + q);

		if (StringUtils.isBlank(q) || StringUtils.isBlank(apiKey))
			throw new TheOnlyPossibleException();
		return random(answers).get(); // you may crash..
	}
}