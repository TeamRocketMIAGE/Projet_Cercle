package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Utilisateur, Long> {

		Utilisateur findByPseudo(String pseudo);
	    List<Utilisateur> findByNom(String nom);
}

