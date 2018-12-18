package guru.bonacci.oogway.doorway.events;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.internals.MaterializedInternal;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.domain.EnquiryEvent;

public class HitCounter {

	
	@EnableBinding(Binding.class)
	public static class Counter {

		@StreamListener(Binding.INPUT)
		public void process(KStream<String, EnquiryEvent> events) {
			events.print("was here");
			events.groupByKey()
				  .windowedBy(TimeWindows.of(1000))
				  .count(MaterializedInternal.as("hits"));
		}
	}

}
