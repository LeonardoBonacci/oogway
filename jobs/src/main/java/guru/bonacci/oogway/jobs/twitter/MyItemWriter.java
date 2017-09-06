package guru.bonacci.oogway.jobs.twitter;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class MyItemWriter implements ItemWriter<Person> {

	@Override
	public void write(List<? extends Person> items) throws Exception {
		items.forEach(System.out::println);
	}
}
