package guru.bonacci.oogway.util;

import java.util.Random;

public class RandomUtils {
	
    public static int fromZeroExclTo(int to) {
		return new Random().nextInt(to);
    }

    public static int fromOneInclTo(int to) {
		return fromZeroExclTo(to);
    }
}
