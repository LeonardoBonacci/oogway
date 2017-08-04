package guru.bonacci.oogway.oracle.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OracleSource {

    String CHANNEL_NAME = "wisdom";

    @Output
    MessageChannel wisdom();
}
