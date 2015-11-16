package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
	ChatMessage findById(long id);
	List<ChatMessage> findByName(String name);//le nom de la personne qui a envoyé le message
}
