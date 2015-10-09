package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CercleRepository extends CrudRepository<Cercle, Long> {

		Cercle findById(long id);
	    List<Cercle> findByName(String name);
}
