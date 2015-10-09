package main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		/*
		 * model.addAttribute("prenom", currentUser.getPrenom());
		 * model.addAttribute("nom", currentUser.getNom());
		 * model.addAttribute("adresse_rue", currentUser.getAdresse_rue());
		 * model.addAttribute("adresse_CP", currentUser.getAdresse_CP());
		 * model.addAttribute("adresse_ville", currentUser.getAdresse_ville());
		 * model.addAttribute("mail", currentUser.getMail());
		 * model.addAttribute("tel", currentUser.getTel());
		 */

		return "user_param";

	}

	@RequestMapping(value = "/user_param", method = RequestMethod.POST, params="submit=Enregistrer les modifications")
	public String requestModificationUserSave(Utilisateur user,
			RedirectAttributes redirectAttributes) {

		/*
		 * if (user.getNom().equals((String)"") ||
		 * user.getPrenom().equals((String)"") ||
		 * user.getMail().equals((String)"")) { //error, Les champs obligatoires
		 * ne sont pas tous remplis !!!
		 * System.out.println("Un champ est vide !!!!");
		 * redirectAttributes.addAttribute("modification", "champ_vide"); return
		 * "redirect:/user_param"; } else {
		 */
		// ok, sauvegarde des modifications dans la base de données

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
	
	
	
	@RequestMapping(value = "/user_param", method = RequestMethod.POST, params="submit=Annuler")
	public String requestModificationUserCancel(Utilisateur user,
			RedirectAttributes redirectAttributes) {



		redirectAttributes.addAttribute("modification", "cancel");
		return "redirect:/user_page";
		

	}

}
