package guru.bonacci.oogway.oracle.persistence;

import static java.lang.String.format;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import guru.bonacci.oogway.oracle.api.IGem;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
@Document(indexName = "oracle", type = "quote", shards = 1, replicas = 0, refreshInterval = "-1")
public class Gem implements IGem {

	public static final String ESSENCE = "essence";

	@Id
	private String id;

	@Field(type = String, store = true, analyzer = "english", searchAnalyzer = "english")
	private String essence;

	public Gem() {
	}

	/**
	 * We don't want to have multiple ES-documents for the same quotes. In
	 * ElasticSearch there is no concept of uniqueness on fields. Exception is
	 * the _id field. When the _id field is equal ES will update the field. The
	 * ES documentation states that full-text search on an _id field is
	 * possible. Testing proves this wrong. Therefore, as a (temporary) solution
	 * we persist the quote in the _id field to allow uniqueness and in the
	 * essence-field for full-text search
	 */
	public Gem(String essence) {
		this.id = essence;
		this.essence = essence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getEssence() {
		return essence;
	}

	@Override
	public void setEssence(String essence) {
		this.essence = essence;
	}
	
	@Override
    public String toString() {
        return format("JMSGem[essence='%s']", essence);
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
