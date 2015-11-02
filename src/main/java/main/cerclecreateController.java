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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			session.setAttribute("newcercle", newCercle);	
		}
		
    	session.setAttribute("newcercle", newCercle);	
		model.addAttribute("newcercle", newCercle);	
		
		SimpleString new_admin = new SimpleString("");
		model.addAttribute("new_admin", new_admin);
		
		return "cercle_create";
	}
	
	

	
	// cas de l'ajout d'un propriétaire
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="add_admin=Ajouter aux propriétaires")
	public String requestCercleCreateAddAdmin(Cercle newCercle, HttpSession session, SimpleString newAdminPseudo, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());		
		
		if(newAdminPseudo.value.isEmpty())
		{
			redirectAttributes.addAttribute("add_admin_request","empty");
		}
		else 
		{
			Utilisateur newAdmin = userRepository.findByPseudo(newAdminPseudo.value);
			if(newAdmin==null)				
			{
				redirectAttributes.addAttribute("add_admin_request","user_does_not_exist");
			}
			else 
			{
				if(tmp.isAdministrateur(newAdminPseudo.value))
				{
					redirectAttributes.addAttribute("add_admin_request","user_already_admin");
				}
				else 
				{
					if(tmp.isMembre(newAdminPseudo.value))
					{
						redirectAttributes.addAttribute("add_admin_request","user_already_member");
					}
					else
					{
						// tout est ok, l'utilisateur saisi peut être ajouté à la liste des admins du cercle
						
						tmp.addAdministrateur(newAdmin);
					}
				}
			}
		}
			
		
		session.setAttribute("newcercle", tmp);
		//model.addAttribute("newcercle", tmp);			
		//model.addAttribute("new_admin", new SimpleString());
		
		/*
		System.out.println("##########################################");
		System.out.println("###         Création de cercle         ###");
		System.out.println("###                                    ###");
		System.out.println("Tentative d'ajout de l'admin : " + newAdmin.getValue() );
		System.out.println("Admin actuellement sélectionné : " + currentAdmin.getPseudo() );
		System.out.println("Nom du cercle : " + newCercle.getName() );
		System.out.println("###                                    ###");
		System.out.println("##########################################");
		*/
		
		return "redirect:/cercle_create";
		
	}
	
	
	// cas de la suppression d'un propriétaire
	@RequestMapping(value = "/cercle_create", method = RequestMethod.POST, params="delete_admin=Delete")
	public String requestCercleCreateAddAdminPOST(Cercle newCercle, HttpSession session, @RequestParam(value = "delete_pseudo", required = false) String deletedAdmin) {
		
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(newCercle.getName());
		tmp.setDescription(newCercle.getDescription());		
		
		System.out.println("##########################################");
		System.out.println("###         Création de cercle         ###");
		System.out.println("###                                    ###");
		System.out.println("     Admin à supprimer : " + deletedAdmin );
		System.out.println("###                                    ###");
		System.out.println("##########################################");
					
		//tmp.deleteAdministrateur( userRepository.findByPseudo(deletedAdmin));

		
		session.setAttribute("newcercle", tmp);
		//model.addAttribute("newcercle", tmp);			
		//model.addAttribute("new_admin", new SimpleString());
		
		/*
		System.out.println("##########################################");
		System.out.println("###         Création de cercle         ###");
		System.out.println("###                                    ###");
		System.out.println("Tentative d'ajout de l'admin : " + newAdmin.getValue() );
		System.out.println("Admin actuellement sélectionné : " + currentAdmin.getPseudo() );
		System.out.println("Nom du cercle : " + newCercle.getName() );
		System.out.println("###                                    ###");
		System.out.println("##########################################");
		*/
		
		return "redirect:/cercle_create";
		
	}
	
	
	// cas de la suppression d'un propriétaire
	@RequestMapping(value = "/cercle_create_remove_admin/{pseudo}", method = RequestMethod.GET)
	public String requestCercleCreateDeleteAdmin(@PathVariable("pseudo") String adminPseudo, @RequestParam(value = "nom") String nomCercle, HttpSession session)
	{
		Cercle tmp = (Cercle)session.getAttribute("newcercle");	
		
		tmp.setName(nomCercle);
		
		System.out.println("##########################################");
		System.out.println("###         Création de cercle         ###");
		System.out.println("###                                    ###");
		System.out.println("Nom du cercle modifié : " + nomCercle );
		System.out.println("###                                    ###");
		System.out.println("##########################################");
		//tmp.setDescription(newCercle.getDescription());	
		
		tmp.deleteAdministrateur( userRepository.findByPseudo(adminPseudo));
		
		session.setAttribute("newcercle", tmp);
		
		return "redirect:/cercle_create";
	}
	

   
	
}
