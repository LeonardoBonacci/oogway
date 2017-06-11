package guru.bonacci.oogway.util;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * This class does not belong here. 
 * In fact, it does not belong anywhere. 
 * It should not even exist.
 * That why it is here.
 */
public class MyListUtils {

	/**
	 * Random object used by random method. This has to be not local to the
	 * random method so as to not return the same value in the same millisecond.
	 */
	private static final Random RANDOM = new Random();

	public static <E> Optional<E> getRandom(final List<E> collection) {
		return getRandom(collection, RANDOM);
	}

	public static <E> Optional<E> getRandom(final List<E> list, Random random) {
		return Optional.ofNullable(
							isEmpty(list) ? null : list.get(random.nextInt(list.size())));
	}
}
