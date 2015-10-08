package main;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class cercleController {


	
	@RequestMapping(value = "/cercle_page", method = RequestMethod.GET)
	public String requestCreatePageUserHome() {

		// obtention de l'id du cercle
		/*
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	   
		
	    System.out.println("Utilisateur actuellement connecté : " + currentUserPseudo);
		
    	// ajout des contacts de l'utilisateur actuel dans la requête   	
    	model.addAttribute("contacts", currentUser.getContact());    	
  
    	// ajout de tous les utilisateurs dans la requête  	
    	model.addAttribute("users", (List<Utilisateur>)userRepository.findAll());
    	
    	*/
	
		return "cercle_page";

	}
}
