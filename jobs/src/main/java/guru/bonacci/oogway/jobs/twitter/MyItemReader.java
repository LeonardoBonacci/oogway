package guru.bonacci.oogway.jobs.twitter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ItemReader;

public class MyItemReader implements ItemReader<Person> {

	private int nextStudentIndex;
    private List<Person> studentData;
 
    MyItemReader() {
        initialize();
    }
 
    private void initialize() {
        Person tony = new Person("Tony", "Tester");
 
        Person nick = new Person("Nick", "Newbie");

        Person ian = new Person("Ian", "Intermediate");
 
        studentData = Collections.unmodifiableList(Arrays.asList(tony, nick, ian));
        nextStudentIndex = 0;
    }
 
    @Override
    public Person read() throws Exception {
        Person nextStudent = null;
 
        if (nextStudentIndex < studentData.size()) {
            nextStudent = studentData.get(nextStudentIndex);
            nextStudentIndex++;
        }
 
        return nextStudent;
    }
}
