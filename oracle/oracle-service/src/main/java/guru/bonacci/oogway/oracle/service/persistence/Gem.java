package guru.bonacci.oogway.oracle.service.persistence;

import static java.lang.String.format;
import static org.springframework.data.elasticsearch.annotations.FieldIndex.not_analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import guru.bonacci.oogway.secretdomain.IGem;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
@Document(indexName = "oracle", type = "quote", shards = 1, replicas = 0, refreshInterval = "-1")
public class Gem implements IGem {

	public static final String SAID = "saying";
	public static final String AUTHOR = "author";

	@Id
	@JsonIgnore
	private String id;

	@Field(type = String, store = true, analyzer = "english", searchAnalyzer = "english")
	private String saying;

	@Field(type = String, index = not_analyzed)
	private String author;

	//TODO write timeobjectmapper for time conversion
	@JsonIgnore
	//spring data's @CreatedDate doesn't work on elasticsearch, therefore we set our own timestamp
	@Field(type = FieldType.Date)
	private LocalDateTime creation;

	public Gem() {
	}

	/**
	 * We don't want to have multiple ES-documents for the same quotes. In
	 * ElasticSearch there is no concept of uniqueness on fields. Exception is
	 * the _id field. When the _id field is equal ES will update the field. The
	 * ES documentation states that full-text search on an _id field is
	 * possible. Testing proves this wrong. Therefore, as a (temporary) solution
	 * we persist the quote in the _id field to allow uniqueness and in the
	 * saying-field for full-text search
	 */
	public Gem(String saying) {
		this.id = saying;
		this.saying = saying;
	}

	public Gem(String saying, String author) {
		this(saying);
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public LocalDateTime getCreation() {
		return creation;
	}

	public void setCreation(LocalDateTime creation) {
		this.creation = creation;
	}

	@Override
    public String toString() {
        return format("Gem[saying='%s', author='%s']", saying, author);
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
