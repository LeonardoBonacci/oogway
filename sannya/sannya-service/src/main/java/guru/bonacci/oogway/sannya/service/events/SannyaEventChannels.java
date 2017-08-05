package guru.bonacci.oogway.sannya.service.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SannyaEventChannels {

    String SANNYA = "sannya";

    @Output(SANNYA)
    MessageChannel sannyaChannel();
    
    
	String ORACLE = "oracle";

    @Input(ORACLE)
    SubscribableChannel oracleChannel();

}
