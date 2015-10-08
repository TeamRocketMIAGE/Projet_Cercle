package main;



import java.util.ArrayList;
import java.util.List;

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
public class userpageController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/user_page", method = RequestMethod.GET)
	public String requestCreatePageUserHome(Model model) {

		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName();  
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	   
		
	    System.out.println("Utilisateur actuellement connecté : " + currentUserPseudo);
		
    	// ajout des contacts de l'utilisateur actuel dans la requête   	
    	model.addAttribute("contacts", currentUser.getContact());    	
  
    	// ajout de tous les utilisateurs dans la requête  	
    	model.addAttribute("users", (List<Utilisateur>)userRepository.findAll());
    	
    	SimpleString user_added = new SimpleString("");
    	// ajout de la variable qui va permettre de stocker le pseudo d'un utilisateur à ajouter dans les contacts
    	model.addAttribute("user_added", user_added);
	
    	
    	// ajout de la liste des demandes extérieures d'ajout  à la liste des contacts de l'utilisateur connecté
    	model.addAttribute("new_contact_to_confirm", currentUser.getAddRequestContacts() );
    	
		return "user_page";

	}
	
	
    
  
    @RequestMapping(value = "/user_page/adduser", method = RequestMethod.POST)
    public String addUserRequest(SimpleString user_added, RedirectAttributes redirectAttributes)
    {
    	
    	
    	
    	System.out.println("Demande d'ajout de l'utilisateur suivant : " + user_added.value);
    	
    	if(userRepository.findByPseudo(user_added.value)==null)
    	{
    		System.out.println("L'utilisateur " + user_added.value + " n'existe pas dans la base de données.");
    		redirectAttributes.addAttribute("add_request","user_does_not_exist");
    	}
    	else
    	{
    		// obtention de l'id de l'utilisateur connecté
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	    String currentUserPseudo = auth.getName();  
    	    
    	    if(currentUserPseudo.equals(user_added.value))
    	    {
    	    	redirectAttributes.addAttribute("add_request","you_cannot_add_yourself");
    	    }
    	    else
    	    {
    	    	Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	
    	    	int i=0;
    	    	for (; i<currentUser.getContact().size() && !currentUser.getContact().get(i).getPseudo().equals(user_added.value); i++);
    	    	if (i<currentUser.getContact().size())
    	    	{
    	    		redirectAttributes.addAttribute("add_request","user_already_in_list");
    	    	}
    	    	else
    	    	{
    	    		Utilisateur askedUser = (Utilisateur)userRepository.findByPseudo(user_added.value);	
    	    		i=0;
        	    	for (; i<askedUser.getAddRequestContacts().size() && askedUser.getAddRequestContacts().get(i).getPseudo().equals(currentUserPseudo); i++);
        	    	if (i<askedUser.getAddRequestContacts().size())
        	    	{
        	    		redirectAttributes.addAttribute("add_request","user_already_requested");
        	    	}
        	    	else
        	    	{
        	    		// tout est ok, il faut ajouter la demande dans la liste des demandes de user_added
        	    		askedUser.addRequestNewContact(currentUser);
        	    		userRepository.save(askedUser);
        	    		redirectAttributes.addAttribute("add_request","ok");
        	    		
        	    	}
    	    	}    	    		
    	    }  	    
    	     
    	    
    	    
    	}
    	
    	
    	
    	
    	
    	
    	return "redirect:/user_page";
    	
    }
    
    
    @RequestMapping(value = "/user_page/confirm_adduser", method = RequestMethod.POST, params="confirm_add=Accepter")
    public String confirmAddUserRequestt(SimpleString user_added_confirmed)
    {
    
    	System.out.println("Confirmation de la demande d'ajout de l'utilisateur " + user_added_confirmed.value + ".");
		
    	// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserPseudo = auth.getName(); 
	    
	    // ajout du nouvel utilisateur dans sa liste de contact
	    Utilisateur currentUser = (Utilisateur)userRepository.findByPseudo(currentUserPseudo);	
	    Utilisateur askingUser = (Utilisateur)userRepository.findByPseudo(user_added_confirmed.value);	    	
	    currentUser.addContact(askingUser);	    
	    currentUser.deleteRequestNewContact(askingUser);
	    userRepository.save(currentUser);
	    
	    askingUser.addContact(currentUser);
	    userRepository.save(askingUser);    
	    
    	
    	return "redirect:/user_page";
    }
    
    @RequestMapping(value = "/user_page/confirm_adduser", method = RequestMethod.POST, params="confirm_add=Refuser")
    public String refuseAddUserRequestt(SimpleString user_added_confirmed)
    {
    
    	System.out.println("Refus de la demande d'ajout.");
    	return "redirect:/user_page";
    }
}
