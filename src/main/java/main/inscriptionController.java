package main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class inscriptionController {
	
	@Autowired
	 UserRepository ur;
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
	public String requestCreatePageInscription(Model model) {

		model.addAttribute("user", new User());
		return "inscription";

	}

	
	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public String requestInscription(User user, RedirectAttributes redirectAttributes) {

		if(ur.findByPseudo(user.getPseudo())!=null)
		{
			System.out.println("Une personne a essayé de s'inscrire avec un pseudo déjà existant : " + user.getPseudo());
			redirectAttributes.addAttribute("user_exist", "true");
			return "redirect:/inscription";
		}
			
		
		ur.save(user);	
		redirectAttributes.addAttribute("signin", "ok");
		
		
		System.out.println("L'utilisateur " + user.getPseudo() + " s'est inscrit.");
		
		return "redirect:/login";

	}
	


}
