package guru.bonacci.spectre.sentiment.services;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import guru.bonacci.spectreshared.persistence.Spec;

/**
 * Specific fields for sentiment
 */
public class SentimentSpec extends Spec {

	@Field(type = FieldType.String)
	public String sentiment;
}
