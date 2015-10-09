package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class cerclecreateController {

	@Autowired
	CercleRepository cr;
	
	@Autowired
	UserRepository ur;
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.GET)
	public String requestCreatePageCercleCreate(Model model) {
		
		model.addAttribute("cercle", new Cercle());
		return "cercle_create";
	}
	
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST)
	public String requestCercleCreate(Cercle cercle, RedirectAttributes redirectAttributes) {
		

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) ur.findByPseudo(currentUserPseudo);
		
		cercle.addAdministrateur(currentUser);
		
		cr.save(cercle);
		System.out.println("Le cercle " + cercle.getName() + " a été créé.");
		System.out.println("Sa description" + cercle.getDescription());
		//faut t'il envoyé le cercle qu on a créé pour aller sur la page du cercle ???
		return "redirect:/cercle_page";
	}
	
}
