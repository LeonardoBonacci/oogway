package guru.bonacci.oogway.oracle.persistence;

import guru.bonacci.oogway.relastic.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for use in
 * jewelry. In this context: wisdom is a gem of infinite value.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gem extends BaseObject {

	public static final String SAYING = "saying";
	public static final String AUTHOR = "author";

	@NonNull
	private String saying;
	private String author;
}
