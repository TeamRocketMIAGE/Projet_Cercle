package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FichierRepository extends CrudRepository<Fichier, Long> {

		Fichier findById(long id);
	    List<Fichier> findByName(String name);
}
