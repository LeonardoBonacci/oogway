package guru.bonacci.oogway.doorway.lumber;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class Lumberjack {

	
	@Autowired
	private QueryableStoreRegistry queryableStoreRegistry;
	
	public Mono<Boolean> isGreedy(String apikey) {
		ReadOnlyWindowStore<Object, Long> keyValueStore =
				queryableStoreRegistry.getQueryableStoreType("hits", QueryableStoreTypes.windowStore());
	
		long hits, now = System.currentTimeMillis();
		try (WindowStoreIterator<Long> it = keyValueStore.fetch(apikey.getBytes(), now - 60000, now)) {
			Spliterator<KeyValue<Long, Long>> spliterator = Spliterators.spliteratorUnknownSize(it, Spliterator.NONNULL);
			Stream<KeyValue<Long, Long>> stream = StreamSupport.stream(spliterator, false);
			hits = stream.map(kv -> kv.value).mapToLong(Long::longValue).sum();
		};
		return Mono.fromSupplier(() -> hits > 10);

	}
}