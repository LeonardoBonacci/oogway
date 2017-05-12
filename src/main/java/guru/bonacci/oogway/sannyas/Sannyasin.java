package guru.bonacci.oogway.sannyas;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Sannyasin: Seeker for Truth.
 */
public interface Sannyasin {

	List<Function<String,String>> preprocessingSteps();

	List<String> seek(String truth);

	List<Predicate<String>> postfilteringStep();
}
