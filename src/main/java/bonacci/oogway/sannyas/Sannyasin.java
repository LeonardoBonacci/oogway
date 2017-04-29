package bonacci.oogway.sannyas;

import java.util.List;
import java.util.function.Function;

public interface Sannyasin {

	List<Function<String,String>> preproces();

	List<String> seek(String truth);
}