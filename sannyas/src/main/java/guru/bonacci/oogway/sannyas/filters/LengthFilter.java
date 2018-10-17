package guru.bonacci.oogway.sannyas.filters;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.extern.slf4j.Slf4j;

/**
 * Puts a limit on the length of the quotes to be indexed
 * 
 * In geometric measurements, length is the most extended dimension of an
 * object. In the International System of Quantities, length is any quantity
 * with dimension distance. In other contexts "length" is the measured dimension
 * of an object. Length may be distinguished from height, which
 * is vertical extent, and width or breadth, which are the distance from side to
 * side, measuring across the object at right angles to the length. Length is a
 * measure of one dimension, whereas area is a measure of two dimensions (length
 * squared) and volume is a measure of three dimensions (length cubed). In most
 * systems of measurement, the unit of length is a base unit, from which other
 * units are defined.
 */
@Component
@Slf4j
public class LengthFilter implements Predicate<GemCarrier> {

	@Value("${filter.maxlength:1000}")
	private Integer maxLength;
	
	@Override
	public boolean test(GemCarrier gem) {
		String input = gem.getSaying();
		log.debug("check: " + input);

		boolean result = input.length() < maxLength;
		if (!result) 
			log.debug("failed: " + input);

		return result;
	}
}
