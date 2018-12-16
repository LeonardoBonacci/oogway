package guru.bonacci.oogway.doorway.events;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.internals.MaterializedInternal;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.domain.EnquiryEvent;

public class HitCounter {

	@EnableBinding(Binding.class)
	public static class WordCounter {

		@StreamListener(Binding.INPUT)
		public void process(KStream<Object, EnquiryEvent> events) {
			events.groupByKey()
				  .windowedBy(TimeWindows.of(1000))
				  .count(MaterializedInternal.as("hits"));
		}
	}

}
