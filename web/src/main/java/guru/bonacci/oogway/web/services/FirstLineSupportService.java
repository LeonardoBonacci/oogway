package guru.bonacci.oogway.web.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.api.IGem;
import guru.bonacci.oogway.oracle.client.OracleRESTClient;
import guru.bonacci.oogway.web.cheaters.Postponer;
import guru.bonacci.oogway.web.intercept.WatchMe;

/**
 * Tier I is the initial support level responsible for basic customer issues. It
 * is synonymous with first-line support, level 1 support, front-end support,
 * support line 1, and various other headings denoting basic level technical
 * support functions. The first job of a Tier I specialist is to gather the
 * customer’s information and to determine the customer’s issue by analyzing the
 * symptoms and figuring out the underlying problem. When analyzing the
 * symptoms, it is important for the technician to identify what the customer is
 * trying to accomplish so that time is not wasted on "attempting to solve a
 * symptom instead of a problem."
 */
@Service
public class FirstLineSupportService {

	private static final String TALK_TO = "I say unto you";
	private static final String TALK_TO_REG_EXPR = "I say unto you '\\w+':";
	
	@Autowired
	private OracleRESTClient oracleClient;
	
	@Autowired
	private Postponer postponer;

	
	@WatchMe
	public String enquire(String input) {
		if (isEmpty(input))
			return "No question no answer..";

		Tuple analyzedInput = analyzeInput(input);
		Optional<IGem> gem = oracleClient.consult(analyzedInput.question, analyzedInput.by);
		return gem.map(IGem::getSaying).orElse(postponer.saySomething());
	}	
	
	private Tuple analyzeInput(String input) {
		Tuple t = new Tuple();

		if (input.startsWith(TALK_TO) && input.matches(TALK_TO_REG_EXPR)) {
			// special case!!! input matches  
			// I say unto you 'author': some question
			
			// retrieve by..
			int fromBy = input.indexOf("'") + 1;
			int closeBy = input.indexOf("'", fromBy);
			t.by = input.substring(fromBy, closeBy);

			// ..and retrieve the question
			int fromQuestion = input.indexOf(":") + 1;
			t.question = StringUtils.trim(input.substring(fromQuestion));
			return t;
		} else {	
			t.question = input;
			return t;
		}
	}	

	class Tuple {
		String question;
		String by;
	}
}
