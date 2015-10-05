package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CercleRepository extends CrudRepository<Cercle, Long> {

	    List<Cercle> findByNom(String nom);
}
