package main;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class userparamController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/user_param", method = RequestMethod.GET)
	public String requestCreatePageUserParam(Model model) {

		// obtention de l'id de l'utilisateur
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository
				.findByPseudo(currentUserPseudo);

		// System.out.println("ceci est un test dans userparamcontroller hehe");
		model.addAttribute("user", currentUser);

		return "user_param";
	}

	@RequestMapping(value = "/user_param", method = RequestMethod.POST, params="submit=Enregistrer les modifications" )
	public String requestModificationUserSave(@Valid Utilisateur user, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {


		if(bindingResult.hasErrors())
		{
			
			model.addAttribute("user", user);
			return "user_param";	
		}
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository
				.findByPseudo(currentUserPseudo);

		currentUser.setAdresse_CP(user.getAdresse_CP());
		currentUser.setAdresse_rue(user.getAdresse_rue());
		currentUser.setAdresse_ville(user.getAdresse_ville());
		currentUser.setMail(user.getMail());
		currentUser.setNom(user.getNom());
		currentUser.setPrenom(user.getPrenom());
		currentUser.setTel(user.getTel());

		System.out.println("mail:" + user.getMail() + "ville" + user.getMail());
		userRepository.save(currentUser);

		redirectAttributes.addAttribute("modification", "ok");
		
		return "redirect:/user_page";	
	}
	
	
	


}
