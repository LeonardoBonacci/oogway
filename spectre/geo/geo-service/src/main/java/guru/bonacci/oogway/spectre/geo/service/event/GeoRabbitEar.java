package guru.bonacci.oogway.spectre.geo.service.event;

import static guru.bonacci.oogway.spectre.geo.service.event.GeoEventChannels.SPECTRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;
import guru.bonacci.oogway.spectre.geo.service.services.GeoService;

@Component
public class GeoRabbitEar {

	@Autowired
	private GeoService service;
	
    @StreamListener(SPECTRE)
	public void onMessage(_1984 _1984) {
		System.out.println(_1984.getMessage());
    	service.index(_1984.getIP(), _1984.getMessage());
    }
}
