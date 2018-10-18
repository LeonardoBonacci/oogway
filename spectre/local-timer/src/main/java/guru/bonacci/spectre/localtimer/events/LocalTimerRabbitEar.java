package guru.bonacci.spectre.localtimer.events;

import org.springframework.stereotype.Component;

import guru.bonacci.spectre.localtimer.services.LocalTimerService;
import guru.bonacci.spectre.utilities.events.SpectreRabbitEar;

@Component
public class LocalTimerRabbitEar extends SpectreRabbitEar {

	public LocalTimerRabbitEar(LocalTimerService service) {
		super(service);
	}
}
