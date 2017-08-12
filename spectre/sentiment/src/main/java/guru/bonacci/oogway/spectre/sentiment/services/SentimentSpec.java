package guru.bonacci.oogway.spectre.sentiment.services;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import guru.bonacci.oogway.spectre.secretpersistence.Spec;

public class SentimentSpec extends Spec {

	@Field(type = FieldType.String)
	public String sentiment;
}
