package guru.bonacci.oogway.spectre.geo.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SpectreSource {

    String CHANNEL_NAME = "worksChannel";

    @Output
    MessageChannel worksChannel();
}
