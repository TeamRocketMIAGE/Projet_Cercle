package main;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String requestCreatePageCercleCreate(Model model, HttpServletRequest request) {
		
		
		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	 
		
		HttpSession session = request.getSession();
		
		String cercleName = (String)session.getAttribute("newcercle_name");
		String cercleDescription = (String)session.getAttribute("newcercle_description");		
		List<Utilisateur> cercleAdmins = (List<Utilisateur> )session.getAttribute("newcercle_admins");
		
		if(cercleName == null) { cercleName = new String(); }
		if(cercleDescription == null) { cercleDescription = new String(); }
		
    	if (cercleAdmins == null)
    	{
    		cercleAdmins =  new ArrayList<Utilisateur>();
    		cercleAdmins.add(currentUser);
    	}
		
		
    	session.setAttribute("newcercle_name", cercleName);
    	session.setAttribute("newcercle_description", cercleDescription);
    	session.setAttribute("newcercle_admins", cercleAdmins);
			
				
		//model.addAttribute("admins", administrateurs);
		//model.addAttribute("members", new ArrayList<Utilisateur>());
		
		SimpleString new_admin = new SimpleString("");
		model.addAttribute("new_admin", new_admin);
		
		return "cercle_create";
	}
	
	
	/*
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
	*/
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="add_admin=Ajouter aux propriétaires")
	public String requestCercleCreate(SimpleString new_admin, RedirectAttributes redirectAttributes) {
		

		System.out.println("Création de cercle : tentative d'ajout de l'admin " + new_admin.getValue() );
		//return "cercle_create";
		return "redirect:/cercle_create";
	}
	
	
   
	
}
