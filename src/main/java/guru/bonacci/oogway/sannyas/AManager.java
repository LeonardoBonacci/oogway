package guru.bonacci.oogway.sannyas;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.es.ARepository;
import guru.bonacci.oogway.es.Juwel;
import guru.bonacci.oogway.jms.SmokeSignal;

/**
 * A manager alone cannot perform all the tasks assigned to him. In order to
 * meet the targets, the manager should delegate authority. Delegation of
 * Authority means division of authority and powers downwards to the
 * subordinate. Delegation is about entrusting someone else to do parts of your
 * job. Delegation of authority can be defined as subdivision and sub-allocation
 * of powers to the subordinates in order to achieve effective results.
 */
@Component
public class AManager implements MessageListener {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ARepository repository;

	@Autowired
	private MessageConverter messageConverter;

	@Override
	public void onMessage(Message message) {
		try {
			// The purposeful life of a manager:
			// to receive an order..
			String input = ((SmokeSignal) messageConverter.fromMessage(message)).getMessage();
			System.out.println("Received <" + input + ">");

			// and to then delegate..
			delegate(input);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delegate(String input) {
		Collection<Sannyasin> sannyas = applicationContext.getBeansOfType(Sannyasin.class).values();
		// Seeking consists of four steps
		for (Sannyasin sannya : sannyas) {
			// pre-proces the input
			Function<String, String> preprocessing = sannya.preproces().stream().reduce(Function.identity(), Function::andThen);
			String preprocessedInput = preprocessing.apply(input);
			// acquire wisdom
			List<String> found = sannya.seek(preprocessedInput);
			// filter the wisdom..
			Predicate<String> postFiltering = sannya.postfilters().stream().reduce(p -> true, Predicate::and);
			found.stream().filter(postFiltering).forEach(f -> repository.index(new Juwel(f)));
			; // ..and persist it
		}
	}

}
