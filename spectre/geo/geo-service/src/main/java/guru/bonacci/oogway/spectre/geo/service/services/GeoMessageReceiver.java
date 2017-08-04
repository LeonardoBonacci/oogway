package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

@Component
public class GeoMessageReceiver {

	@Autowired
	private GeoService service;
	
    @StreamListener(SpectreSink.CHANNEL_NAME)
	public void onMessage(_1984 _1984) {
		System.out.println(_1984.getMessage());
    	//	service.index(_1984.getIP(), _1984.getMessage());
    }

}
