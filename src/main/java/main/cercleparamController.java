package main;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class cercleparamController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/cercle_param", method = RequestMethod.GET)
	public String requestCreatePageCercleParam(Model model) {
		
		//Cercle currentCercle = (Cercle)CercleRepository.findByName("??????????");
		
		//model.addAttribute("cercle", currentCercle);
		
		
/*
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	   
		
	    
	    System.out.println("ceci est un test dans userparamcontroller hehe");
	    
	    model.addAttribute("prenom", currentUser.getPrenom());

*/
		return "cercle_param";

	}
	
}
