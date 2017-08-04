package guru.bonacci.oogway.sannya.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SannyaSource {

    String CHANNEL_NAME = "truth";

    @Output
    MessageChannel truth();
}
