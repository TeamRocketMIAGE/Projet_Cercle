package main;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class cerclecreateController {
	
	@Autowired
	CercleRepository cercleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/cercle_create", method = RequestMethod.GET)
	public String requestCreatePageCercleCreate(Model model, HttpSession session) {
		
		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	 
	    
	    Cercle newCercle = (Cercle)session.getAttribute("newcercle");				
		if(newCercle == null) 
		{ 
			newCercle = new Cercle(); 
			newCercle.addAdministrateur(currentUser);			
		}
		
    	session.setAttribute("newcercle", newCercle);	
		model.addAttribute("newcercle", newCercle);	
		
		// -> new_admin_member.value1 contient la saisie du pseudo d'un nouvel ADMIN à ajouter à la liste des administrateurs du cercle
		// -> new_admin_member.value2 contient la saisie du pseudo d'un nouveau MEMBRE à ajouter à la liste des membres du cercle
		DoubleString new_admin_member = new DoubleString("","");
		model.addAttribute("new_admin_member", new_admin_member);	
		
		return "cercle_create";
	}
	
	

	
	// cas de l'ajout d'un propriétaire
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="add_admin=Ajouter aux propriétaires")
	public String requestCercleCreateAddAdmin(Cercle newCercle, HttpSession session, DoubleString newAdminMember, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());		
		
		if(newAdminMember==null || newAdminMember.value1.isEmpty())		
			redirectAttributes.addAttribute("add_admin_request","empty");		
		else 
		{
			Utilisateur newAdmin = userRepository.findByPseudo(newAdminMember.value1);
			if(newAdmin==null)	
				redirectAttributes.addAttribute("add_admin_request","user_does_not_exist");
			else 
			{
				if(tmp.isAdministrateur(newAdminMember.value1))
					redirectAttributes.addAttribute("add_admin_request","user_already_admin");
				else 
				{
					if(tmp.isMembre(newAdminMember.value1))
						redirectAttributes.addAttribute("add_admin_request","user_already_member");
					else
					{
						// tout est ok, l'utilisateur saisi peut être ajouté à la liste des admins du cercle
						
						tmp.addAdministrateur(newAdmin);
					}
				}
			}
		}
					
		session.setAttribute("newcercle", tmp);	
		
		return "redirect:/cercle_create";		
	}
	
	
	// cas de la suppression d'un propriétaire
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="delete_admin=Supprimer")
	public String requestCercleCreateDeleteAdmin(Cercle newCercle, HttpSession session, @RequestParam(value = "delete_admin_pseudo", required = false) String deletedAdmin) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());		
		
		/*
		System.out.println("##########################################");
		System.out.println("###         Création de cercle         ###");
		System.out.println("###                                    ###");
		System.out.println("     Admin à supprimer : " + deletedAdmin );
		System.out.println("###                                    ###");
		System.out.println("##########################################");
		*/
					
		tmp.deleteAdministrateur( userRepository.findByPseudo(deletedAdmin));
		
		session.setAttribute("newcercle", tmp);
		
		return "redirect:/cercle_create";
		
	}
	
	
	
	
	// cas de l'ajout d'un membre
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="add_member=Ajouter aux membres")
	public String requestCercleCreateAddMember(Cercle newCercle, HttpSession session, DoubleString newAdminMember, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());		
		
		if(newAdminMember==null || newAdminMember.value2.isEmpty())		
			redirectAttributes.addAttribute("add_member_request","empty");		
		else 
		{
			Utilisateur newMember = userRepository.findByPseudo(newAdminMember.value2);
			if(newMember==null)	
				redirectAttributes.addAttribute("add_member_request","user_does_not_exist");
			else 
			{
				if(tmp.isAdministrateur(newAdminMember.value2))
					redirectAttributes.addAttribute("add_member_request","user_already_admin");
				else 
				{
					if(tmp.isMembre(newAdminMember.value2))
						redirectAttributes.addAttribute("add_member_request","user_already_member");
					else
					{
						// tout est ok, l'utilisateur saisi peut être ajouté à la liste des membres du cercle
						
						tmp.addUtilisateur(newMember);
					}
				}
			}
		}
					
		session.setAttribute("newcercle", tmp);	
		
		return "redirect:/cercle_create";		
	}

	
	
	// cas de la suppression d'un membre
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="delete_member=Supprimer")
	public String requestCercleCreateDeleteMember(Cercle newCercle, HttpSession session, @RequestParam(value = "delete_member_pseudo", required = false) String deletedMember) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());				
					
		tmp.deleteUtilisateur( userRepository.findByPseudo(deletedMember));
		
		session.setAttribute("newcercle", tmp);
		
		return "redirect:/cercle_create";
		
	}	
	
	
	// cas de la validation de la création du cercle
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="submit_ok=Créer le cercle")
	public String requestCercleCreateValidation(Cercle newCercle, HttpSession session) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());				
			
		cercleRepository.save(tmp);
		
		List<Utilisateur> tmp_admins = tmp.getAdministrateurs();
		List<Utilisateur> tmp_members = tmp.getUtilisateurs();
		
		for(Utilisateur a : tmp_admins)
		{
			a.addCercles_admin(tmp);
			userRepository.save(a);
		}
		
		for(Utilisateur m : tmp_members)
		{
			m.addCercles_membre(tmp);
			userRepository.save(m);
		}	
		
				
		session.removeAttribute("newcercle");
		
		return "redirect:/user_page";
		
	}	
	
	// cas de l'annulation de la création du cercle
	@RequestMapping(value = "/cercle_create_cancel", method = RequestMethod.GET)
	public String requestCercleCreateCancel(HttpSession session) {
		
		session.removeAttribute("newcercle");
		
		return "redirect:/user_page";
	}
	
	// accès au paramètre utilisateur à partir de la page création de cercle
	// il faut supprimer l'attribut en session
	@RequestMapping(value = "/cercle_create_go_user_param", method = RequestMethod.GET)
	public String requestCercleCreateGoUserParam(HttpSession session) {
		
		session.removeAttribute("newcercle");
		
		return "redirect:/user_param";
	}
   
	
	// tentative d'accès à la page à partir du bouton "Créer un nouveau cercle"
	@RequestMapping(value = "/cercle_create_access", method = RequestMethod.GET)
	public String requestCercleCreateAccess(HttpSession session) {
		
		session.removeAttribute("newcercle");
		
		return "redirect:/cercle_create";
	}
   
	
}
