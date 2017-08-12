package guru.bonacci.oogway.oracle.service.events;

import static guru.bonacci.oogway.oracle.service.events.OracleEventChannels.SANNYA;
import static org.slf4j.LoggerFactory.getLogger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

    @StreamListener(SANNYA)
	public void onMessage(GemCarrier event) {
		logger.info("Receiving an extra bit of knowledge: '" + event + "'");
		repo.saveTheNewOnly(new Gem(event.getSaying(), event.getAuthor()));
	}
    
//    @PostConstruct
//    private void someInitialData() {
//    	repo.saveTheNewOnly(
//    		new Gem("Yesterday is history, tomorrow is a mystery, and today is a gift... that's why they call it present", "Master Oogway"),
//    		new Gem("Your story may not have such a happy beginning but that does not make you who you are, it is the rest of it- who you choose to be", "Soothsayer from Kung Fu Panda 2"),
//    		new Gem("There is no secret ingredient", "Mr Ping, Po's father"),
//    		new Gem("There are no coincidences in this world", "Turtle in Kung Fu Panda"),
//			new Gem("We do not wash our pits in the pool of sacred tears", "Shifu"),
//    		new Gem("You must let go of the illusion of control", "The Turtle"),
//    		new Gem("You must believe!", "The Turtle"),
//    		new Gem("Your mind is like this water, my friend. When it is agitated, it becomes difficult to see. But if you allow it to settle, the answer becomes clear.", "Kung Fu Panda"),
//    		new Gem("Legend tells of a legendary warrior whose kung fu skills were the stuff of legend.", "Kung Fu Panda"),
//    		new Gem("If you only do what you can do, you will never be more than who you are.", "Master Shifu"));	
//    }
}
