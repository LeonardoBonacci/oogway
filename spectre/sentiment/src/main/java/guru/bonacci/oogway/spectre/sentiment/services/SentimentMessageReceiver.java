package guru.bonacci.oogway.spectre.sentiment.services;

import java.io.IOException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SentimentMessageReceiver {

	@JmsListener(destination = "first-topic")
	public void onMessage(String input) throws IOException {
		System.out.println(input);
	}
}
