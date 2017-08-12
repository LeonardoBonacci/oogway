package guru.bonacci.oogway.spectre.secretpersistence;

import java.io.IOException;

import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepositoryCustom {
	
	void addData(String key, Object nestedObject, Spec s) throws IOException;
}
