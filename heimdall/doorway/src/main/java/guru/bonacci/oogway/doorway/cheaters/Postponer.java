package guru.bonacci.oogway.doorway.cheaters;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static guru.bonacci.oogway.utilities.CustomListUtils.random;
import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Postponer - someone who postpones work (especially out of laziness or
 * habitual carelessness)
 */
@Component
@Slf4j
public class Postponer {

	@Value("${file.name.answers.to.win.time:}")
	private String fileName;

	private List<String> answers;

	@PostConstruct
	public void setup() {
		try {
			answers = readToList(fileName);
		} catch (IOException e) {
			log.error("Can't read file: " + fileName);
		} finally {
			if (isEmpty(answers))
				answers = singletonList("I'm speechless, are you sure?");
		}
	}
	
	public String saySomething() {
		return random(answers).get();
	}
}
