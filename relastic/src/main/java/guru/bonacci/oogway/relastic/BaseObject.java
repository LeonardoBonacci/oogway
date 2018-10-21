package guru.bonacci.oogway.relastic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseObject {

	@JsonIgnore
	@NonNull
	private String id;
}
