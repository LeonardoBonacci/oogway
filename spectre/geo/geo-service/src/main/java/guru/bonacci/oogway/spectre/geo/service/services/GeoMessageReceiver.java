package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

@Component
public class GeoMessageReceiver {

	@Autowired
	private GeoService service;
	
	@JmsListener(destination = "${spring.activemq.queue.to-geo}")
	public void onMessage(_1984 _1984) {
		service.index(_1984.getIP(), _1984.getMessage());
	}
}
