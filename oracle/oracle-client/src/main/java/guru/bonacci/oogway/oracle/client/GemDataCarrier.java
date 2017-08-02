package guru.bonacci.oogway.oracle.client;

import static java.lang.String.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import guru.bonacci.oogway.oracle.api.IGem;

/**
 * Data donkey
 */
public class GemDataCarrier implements IGem {

	private String saying;

	private String author;

	public GemDataCarrier() {
	}

	public GemDataCarrier(String saying) {
		this.saying = saying;
	}

	public GemDataCarrier(String saying, String author) {
		this(saying);
		this.author = author;
	}

	@Override
	public String getSaying() {
		return saying;
	}

	@Override
	public void setSaying(String saying) {
		this.saying = saying;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return format("GemDataCarrier[saying='%s', author='%s']", saying, author);
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
