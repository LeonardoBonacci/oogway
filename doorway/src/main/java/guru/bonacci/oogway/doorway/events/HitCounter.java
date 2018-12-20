package guru.bonacci.oogway.doorway.events;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.internals.MaterializedInternal;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.domain.EnquiryEvent;

@Component
public class HitCounter {

	@StreamListener(Binding.INPUT)
	public void process(KStream<String, EnquiryEvent> events) {
		events.print(Printed.<String, EnquiryEvent>toSysOut().withLabel("hit stream"));
		events.groupByKey()
			  .windowedBy(TimeWindows.of(1000))
			  .count(MaterializedInternal.as("hits"));
	}
}
