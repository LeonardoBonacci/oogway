package guru.bonacci.oogway.oracle.api;

import static java.lang.String.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
public class IGem {

	public static final String ESSENCE = "essence";

	private String id;

	private String essence;

	public IGem() {
	}

	public IGem(String essence) {
		this.id = essence;
		this.essence = essence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEssence() {
		return essence;
	}

	public void setEssence(String essence) {
		this.essence = essence;
	}
	
	@Override
    public String toString() {
        return format("IGem[essence='%s']", essence);
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
