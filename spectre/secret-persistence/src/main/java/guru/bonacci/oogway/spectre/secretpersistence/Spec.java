package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
@Document(indexName = "logstash-spectre", type = "logs", shards = 1, replicas = 0, refreshInterval = "-1")
public class Spec {

	@Id
	private String id;

	public Spec() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
