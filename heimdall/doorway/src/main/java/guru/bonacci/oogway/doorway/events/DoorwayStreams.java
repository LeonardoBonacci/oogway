package guru.bonacci.oogway.doorway.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DoorwayStreams {

    String SPECTRE = "spectre";

    @Output(SPECTRE)
    MessageChannel spectre();
}
