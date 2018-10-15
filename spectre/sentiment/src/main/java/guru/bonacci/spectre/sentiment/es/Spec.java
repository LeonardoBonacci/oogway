package guru.bonacci.spectre.sentiment.es;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spec {

	public static final String MESSAGE = "message";

	@JsonIgnore
	public String id;
	
	public String message;

	public Integer a;
}
