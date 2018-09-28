package guru.bonacci.spectre.money.services;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import guru.bonacci.spectre.spectreshared.persistence.Spec;

/**
 * Specific fields for money
 */
public class MoneySpec extends Spec {

	@Field(type = FieldType.Text)
	public String income;
}
