package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

		User findByPseudo(String pseudo);
	    List<User> findByNom(String nom);
}

