package guru.bonacci.oogway.sannya.service.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SannyaSink {

	String CHANNEL_NAME = "truth";

    @Input
    SubscribableChannel truth();
}
