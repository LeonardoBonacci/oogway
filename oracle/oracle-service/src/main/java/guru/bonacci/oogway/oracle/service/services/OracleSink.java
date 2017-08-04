package guru.bonacci.oogway.oracle.service.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OracleSink {

	String CHANNEL_NAME = "wisdom";

    @Input
    SubscribableChannel wisdom();
}
