package main;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class inscriptionController {
	
	@Autowired
	UserRepository ur;
	
	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
    @Autowired
    public inscriptionController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
	public String requestCreatePageInscription(Model model) {

		model.addAttribute("user", new Utilisateur());
		model.addAttribute("pw_verif", new SimpleString());
		return "inscription";

	}

	
	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public String requestInscription(Utilisateur user, SimpleString pw_verif ,RedirectAttributes redirectAttributes) {

		if(ur.findByPseudo(user.getPseudo())!=null)
		{
			System.out.println("Une personne a essayé de s'inscrire avec un pseudo déjà existant : " + user.getPseudo());
			redirectAttributes.addAttribute("user_exist", "true");	
			pw_verif.setValue("");
			user.setPassword("");
			return "redirect:/inscription";
		}
		
		if (!pw_verif.value.equals(user.getPassword()))
		{
			
			redirectAttributes.addAttribute("same_password", "false");	
			pw_verif.setValue("");
			user.setPassword("");
			return "redirect:/inscription";
		}
					

		inMemoryUserDetailsManager.createUser(new User(user.getPseudo(), user.getPassword(), new ArrayList<GrantedAuthority>()));
				
		System.out.println("L'utilisateur " + user.getPseudo() + " s'est inscrit.");	
		
		pw_verif.setValue("");
		user.setPassword("");
		ur.save(user);	
		redirectAttributes.addAttribute("signin", "ok");
		
		return "redirect:/login";
	}
	


}
