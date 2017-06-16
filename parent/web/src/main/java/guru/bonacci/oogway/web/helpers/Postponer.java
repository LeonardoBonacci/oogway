package guru.bonacci.oogway.web.helpers;

import static org.springframework.util.CollectionUtils.isEmpty;
import static guru.bonacci.oogway.utils.MyFileUtils.readToList;
import static guru.bonacci.oogway.utils.MyListUtils.getRandom;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class Postponer {
	
	private static final String FILE_NAME = "answers-to-win-time.txt";

	private final Logger logger = getLogger(this.getClass());

	private List<String> answers;
	
	@PostConstruct
	public void setup() {
		try {
			answers = readToList(FILE_NAME);
		} catch (IOException e) {
			logger.error("Can't read file: " + FILE_NAME);
		} finally {
			if (isEmpty(answers))
				answers = singletonList("I'm speechless, are you sure?");
		}
	}
	
	public String saySomething() {
		return getRandom(answers).get();
	}
}
