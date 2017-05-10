package guru.bonacci.oogway.util;

import java.util.Random;

public class RandomUtils {
	
    public static Integer fromZeroExclTo(Integer to) {
		return new Random().nextInt(to);
    }

    public static Integer fromOneInclTo(Integer to) {
		return fromZeroExclTo(to);
    }
}
