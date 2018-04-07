package guru.bonacci.oogway.lumberjack.persistence;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, String> {

	int nrOfVisitsBetween(Date from, Date to);
}
