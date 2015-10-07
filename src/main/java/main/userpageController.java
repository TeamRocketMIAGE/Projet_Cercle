package main;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class userpageController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/user_page", method = RequestMethod.GET)
	public String requestCreatePageUserHome(Model model) {

    	// récupération de tous les utilisateurs
    	List<Utilisateur> users = (List<Utilisateur>)userRepository.findAll();
    	
    	model.addAttribute("users", users);
    	
	
		return "user_page";

	}
}
