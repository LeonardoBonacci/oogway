package guru.bonacci.oogway.oracle.client;

import static java.lang.String.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import guru.bonacci.oogway.oracle.api.IGem;

/**
 * Data donkey
 */
public class GemDataCarrier implements IGem {

	private String said;

	private String by;

	public GemDataCarrier() {
	}

	public GemDataCarrier(String said) {
		this.said = said;
	}

	public GemDataCarrier(String said, String by) {
		this(said);
		this.by = by;
	}

	@Override
	public String getSaid() {
		return said;
	}

	@Override
	public void setSaid(String said) {
		this.said = said;
	}

	@Override
	public String getBy() {
		return by;
	}

	@Override
	public void setBy(String by) {
		this.by = by;
	}

	@Override
	public String toString() {
		return format("GemDataCarrier[said='%s', by='%s']", said, by);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
