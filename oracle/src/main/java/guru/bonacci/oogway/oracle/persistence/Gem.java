package guru.bonacci.oogway.oracle.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for use in
 * jewelry. In this context: wisdom is a gem of infinite value.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gem {

	public static final String SAYING = "saying";
	public static final String AUTHOR = "author";

	@JsonIgnore
	private String id;

	private String saying;

	private String author;
}
