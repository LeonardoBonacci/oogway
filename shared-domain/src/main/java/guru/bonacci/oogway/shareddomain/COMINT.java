package guru.bonacci.oogway.shareddomain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * COMINT
 * All intelligence gathered from intercepted communications
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class COMINT implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String ip;
	private String message;
}
