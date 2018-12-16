package guru.bonacci.oogway.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryEvent implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String question, apikey;
}
