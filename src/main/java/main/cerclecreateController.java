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
	CercleRepository cercleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.GET)
	public String requestCreatePageCercleCreate(Model model) {
		
		model.addAttribute("cercle", new Cercle());
		return "cercle_create";
	}
	
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST)
	public String requestCercleCreate(Cercle cercle, RedirectAttributes redirectAttributes) {
		

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);
		
		cercle.addAdministrateur(currentUser);
		currentUser.addCercles_admin(cercle);
		
		cercleRepository.save(cercle);
		userRepository.save(currentUser);
		
		System.out.println("Le cercle " + cercle.getName() + " a été créé.");
		System.out.println("Sa description" + cercle.getDescription());
		
		redirectAttributes.addFlashAttribute(cercle);
		redirectAttributes.addAttribute("cercle", cercle.getId()).addFlashAttribute("message", "cercle créé");
		return "redirect:/cercle_page/{cercle}";
	}
	
}
