package bonacci.oogway.sannyas;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import bonacci.oogway.es.Juwel;
import bonacci.oogway.services.web.ARepository;

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
			String input = (String) messageConverter.fromMessage(message);
	        System.out.println("Received <" + input + ">");

	        Collection<Sannyasin> sannyas = applicationContext.getBeansOfType(Sannyasin.class).values();
			// Seeking consists of four steps
			for (Sannyasin sannya : sannyas) {
				// pre-proces the input
				Function<String,String> preprocessing =	sannya.preproces().stream()
																		  .reduce(Function.identity(), Function::andThen);
				String preprocessed = preprocessing.apply(input);
			
				// acquire wisdom
				List<String> found = sannya.seek(preprocessed);
				
				found.stream()
					 .filter(sannya.postfilter()) // filter the wisdom..
					 .forEach(f -> repository.index(new Juwel(f)));; // ..and persist it
		  }	
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
