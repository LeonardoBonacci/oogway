package guru.bonacci.oogway.spectre.geo.service.event;

import static guru.bonacci.oogway.spectre.geo.service.event.GeoEventChannels.SPECTRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.secretdomain.COMINT;
import guru.bonacci.oogway.spectre.geo.service.services.GeoService;

@Component
public class GeoRabbitEar {

	@Autowired
	private GeoService service;
	
    @StreamListener(SPECTRE)
	public void onMessage(COMINT comint) {
		System.out.println(comint.getMessage());
    	service.index(comint.getIP(), comint.getMessage());
    }
}
