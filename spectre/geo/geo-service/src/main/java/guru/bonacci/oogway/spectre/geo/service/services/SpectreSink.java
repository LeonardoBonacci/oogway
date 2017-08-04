package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SpectreSink {

	String CHANNEL_NAME = "worksChannel";

    @Input
    SubscribableChannel worksChannel();
}
