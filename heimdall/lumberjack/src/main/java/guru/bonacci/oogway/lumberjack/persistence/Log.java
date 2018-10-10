package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document(collection = "visits")
public class Log {

	@Id
	private Instant moment;

	@Field
	private String apiKey;
}
