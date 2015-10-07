package main;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class userparamController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/user_param", method = RequestMethod.GET)
	public String requestCreatePageUserParam(Model model) {

		// obtention de l'id de l'utilisateur
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	   
		
	    
	    System.out.println("ceci est un test dans userparamcontroller hehe");
	    
	    model.addAttribute("prenom", currentUser.getPrenom());
	    model.addAttribute("nom", currentUser.getNom());
	    model.addAttribute("adresse_rue", currentUser.getAdresse_rue());
	    model.addAttribute("adresse_CP", currentUser.getAdresse_CP());
	    model.addAttribute("adresse_ville", currentUser.getAdresse_ville());
	    model.addAttribute("mail", currentUser.getMail());
	    model.addAttribute("tel", currentUser.getTel());

		return "user_param";

	}
	
}
