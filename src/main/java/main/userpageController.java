package main;



import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class userpageController {

	UserRepository ur;
	
	@RequestMapping(value = "/user_page", method = RequestMethod.GET)
	public String requestCreatePageUserHome(Model model) {

    	// récupération
    	List<Utilisateur> users = (List<Utilisateur>)ur.findAll();
    	
    	model.addAttribute("users", users);
    	
	
		return "user_page";

	}
}
