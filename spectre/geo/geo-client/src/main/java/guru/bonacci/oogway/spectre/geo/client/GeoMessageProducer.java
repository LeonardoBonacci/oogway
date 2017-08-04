package guru.bonacci.oogway.spectre.geo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

@Component
public class GeoMessageProducer {

	@Autowired
    private SpectreGateway gateway;

	public void send(String ip, String message) {        
		gateway.generate(new _1984(ip, message));
	}
}
