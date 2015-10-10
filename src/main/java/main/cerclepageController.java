package main;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class cerclepageController {

	@Autowired
	CercleRepository cr;
	
	@Autowired
	UserRepository ur;
	
	@RequestMapping(value = "/cercle_page/{cercle}", method = RequestMethod.GET)
	public String requestCreatePageUserHome(@PathVariable Cercle cercle, Model model) {

		System.out.println("sur al page du cercle: "+ cercle.getId() + cercle.getName());
		//on check si l'utilisateur a le droit d'accéder au cercle
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)ur.findByPseudo(currentUserPseudo);	
		
	    //model.addAllAttributes()
	    if(currentUser.EstAdmin(cercle) || currentUser.Estmembre(cercle)){
	    	System.out.println("le user est bien membre ou admin !");
			return "cercle_page";
	    }
	    else
	    {
	    	return "redirect:user_page";
	    }
	    
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
	

	}
}
