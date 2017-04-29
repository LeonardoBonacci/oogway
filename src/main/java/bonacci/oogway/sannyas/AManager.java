package bonacci.oogway.sannyas;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import bonacci.oogway.jms.SmokeSignal;
import bonacci.oogway.oracle.Juwel;
import bonacci.oogway.web.ARepository;

@Component
public class AManager {

	private final ApplicationContext applicationContext;

	private final ARepository repository;
	
	@Autowired
	public AManager(ApplicationContext applicationContext, ARepository repository) {
		this.applicationContext = applicationContext;
		this.repository = repository;
	}

    @JmsListener(destination = "mailbox", containerFactory = "aFactory")
    public void receiveMessage(SmokeSignal smokeSignal) {
    	String input = smokeSignal.getMessage();
        System.out.println("Received <" + smokeSignal + ">");

        Collection<Sannyasin> sannyas = applicationContext.getBeansOfType(Sannyasin.class).values();
		for (Sannyasin sannya : sannyas) {
			Function<String,String> preprocessing =	sannya.preproces().stream()
																	  .reduce(Function.identity(), Function::andThen);
			String preprocessed = preprocessing.apply(input);
		
			List<String> found = sannya.seek(preprocessed);
			found.forEach(f -> repository.index(new Juwel(f)));;
	  }	}
}
