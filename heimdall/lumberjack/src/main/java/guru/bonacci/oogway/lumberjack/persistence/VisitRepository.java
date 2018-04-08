package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, String> {

	Visit findByName(String name);

	List<Visit> findByMomentBetween(Instant from, Instant until);

	long countByMomentBetween(Instant from, Instant until);
}
