package main;
import java.util.ArrayList;
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
public class cercleparamController {

	@Autowired
	CercleRepository cercleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/cercle_param", method = RequestMethod.GET)
	public String requestCreatePageCercleParam(@RequestParam(value = "cercle") String cercle_id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		
		long currentCercleId = Long.parseLong(cercle_id);
		Cercle currentCercle = cercleRepository.findById(currentCercleId);

		if (currentCercle != null) {
			// obtention de l'id de l'utilisateur connecté
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String currentUserPseudo = auth.getName();

			// vérification que l'user connecté a droit d'accéder à la modification du cercle demandé (= s'il est admin)
			if (currentCercle.isAdministrateur(currentUserPseudo)) {
				
				//Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);
				
				Cercle paramCercle = (Cercle)session.getAttribute("paramcercle");				
				if(paramCercle == null || paramCercle.getId() != currentCercleId) 
				{					
					session.setAttribute("paramcercle", currentCercle);
					model.addAttribute("paramcercle", currentCercle);	
					
					
					List<Utilisateur> tmp_previous_admins = new ArrayList<Utilisateur>();
					for(Utilisateur ua : currentCercle.getAdministrateurs())
						tmp_previous_admins.add(ua);
					List<Utilisateur> tmp_previous_members = new ArrayList<Utilisateur>();
					for(Utilisateur um : currentCercle.getUtilisateurs())
						tmp_previous_members.add(um);
					
					session.setAttribute("paramcercle_previous_admins", tmp_previous_admins);
					session.setAttribute("paramcercle_previous_members", tmp_previous_members);
				}
				else
				{					
					session.setAttribute("paramcercle", paramCercle);
					model.addAttribute("paramcercle", paramCercle);	
				}
				
				
				// -> new_admin_member.value1 contient la saisie du pseudo d'un nouvel ADMIN à ajouter à la liste des administrateurs du cercle
				// -> new_admin_member.value2 contient la saisie du pseudo d'un nouveau MEMBRE à ajouter à la liste des membres du cercle
				DoubleString new_admin_member = new DoubleString("","");
				model.addAttribute("new_admin_member", new_admin_member);
				
				model.addAttribute("currentuser_pseudo", currentUserPseudo);
				
				return "cercle_param";				
				
			} else {
				redirectAttributes.addAttribute("error", "unauthorized_cercle");
			}

		} else {
			redirectAttributes.addAttribute("error", "cercle_does_not_exist");
		}

		// s'il y a un soucis quelque part, renvoyer vers l'espace utilisateur
		return ("redirect:/user_page");		
	}
	
	
	// cas de l'ajout d'un propriétaire
	@RequestMapping(value = "/cercle_param", method = RequestMethod.POST, params="add_admin=Ajouter aux propriétaires")
	public String requestCercleParamAddAdmin(@RequestParam(value = "cercle") String cercle_id, Cercle paramCercle, HttpSession session, DoubleString newAdminMember, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("paramcercle");	
		
		redirectAttributes.addAttribute("cercle", cercle_id);
		
		tmp.setName(paramCercle.getName());
		tmp.setDescription(paramCercle.getDescription());		
		
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
					
		session.setAttribute("paramcercle", tmp);	
		
		return "redirect:/cercle_param";		
	}
	
	
	// cas de la suppression d'un propriétaire
	@RequestMapping(value = "/cercle_param", method = RequestMethod.POST, params="delete_admin=Supprimer")
	public String requestCercleParamDeleteAdmin(@RequestParam(value = "cercle") String cercle_id, Cercle paramCercle, HttpSession session, @RequestParam(value = "delete_admin_pseudo", required = false) String deletedAdmin, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("paramcercle");	
		
		tmp.setName(paramCercle.getName());
		tmp.setDescription(paramCercle.getDescription());			
				
		tmp.deleteAdministrateur( userRepository.findByPseudo(deletedAdmin));
		
		System.out.println("### Test ### - " + deletedAdmin + " va être supprimé des administrateurs");
		
		session.setAttribute("paramcercle", tmp);
		
		redirectAttributes.addAttribute("cercle", cercle_id);
		
		return "redirect:/cercle_param";
		
	}
	
	
	// cas de l'ajout d'un membre
	@RequestMapping(value = "/cercle_param", method = RequestMethod.POST, params="add_member=Ajouter aux membres")
	public String requestCercleParamAddMember(@RequestParam(value = "cercle") String cercle_id, Cercle paramCercle, HttpSession session, DoubleString newAdminMember, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("paramcercle");	
		
		redirectAttributes.addAttribute("cercle", cercle_id);
		
		tmp.setName(paramCercle.getName());
		tmp.setDescription(paramCercle.getDescription());		
		
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
					
		session.setAttribute("paramcercle", tmp);	
		
		return "redirect:/cercle_param";		
	}
	
	
	// cas de la suppression d'un membre
	@RequestMapping(value = "/cercle_param", method = RequestMethod.POST, params="delete_member=Supprimer")
	public String requestCercleParamDeleteMember(@RequestParam(value = "cercle") String cercle_id, Cercle paramCercle, HttpSession session, @RequestParam(value = "delete_member_pseudo", required = false) String deletedMember, RedirectAttributes redirectAttributes) {
		
		Cercle tmp = (Cercle)session.getAttribute("paramcercle");	
		
		tmp.setName(paramCercle.getName());
		tmp.setDescription(paramCercle.getDescription());			
				
		tmp.deleteUtilisateur( userRepository.findByPseudo(deletedMember));
		
		session.setAttribute("paramcercle", tmp);
		
		System.out.println("### Test ### - " + deletedMember + " va être supprimé des membres");
		
		redirectAttributes.addAttribute("cercle", cercle_id);
		
		return "redirect:/cercle_param";
		
	}
	
	
	
	// cas de la validation de la modification du cercle
	@RequestMapping(value = "/cercle_param", method = RequestMethod.POST, params="submit_ok=Enregistrer les modifications")
	public String requestCercleParamValidation(@RequestParam(value = "cercle") String cercle_id, Cercle paramCercle, HttpSession session) {
			
		Cercle tmp = (Cercle)session.getAttribute("paramcercle");
		
		// ces 2 listes permettent de savoir qui étaient admins/membres avant la modification effective du cercle
		// à partir de cela, on pourra en déduire quels sont les admins/membres qui ont éventuellement été retirés du cercle		
		
		List<Utilisateur> previous_admins = (List<Utilisateur>)session.getAttribute("paramcercle_previous_admins");
		List<Utilisateur> previous_members = (List<Utilisateur>)session.getAttribute("paramcercle_previous_members");
		
		List<Utilisateur> tmp_previous_admins = new ArrayList<Utilisateur>();
		for(Utilisateur ua : previous_admins)
			tmp_previous_admins.add(ua);
		List<Utilisateur> tmp_previous_members = new ArrayList<Utilisateur>();
		for(Utilisateur um : previous_members)
			tmp_previous_members.add(um);
		
    	for(Utilisateur uuu : tmp_previous_admins)
    		System.out.println ("### Test Coté cercle ### - " + uuu.getPseudo() + " ETAIT un admin du cercle qui vient juste d'être modifié");
       	for(Utilisateur uuu : tmp_previous_members)
    		System.out.println ("### Test Coté cercle ### - " + uuu.getPseudo() + " ETAIT un membre du cercle qui vient juste d'être modifié");
		
    	for(Utilisateur uuu : tmp.getAdministrateurs())
    		System.out.println ("### Test Coté cercle ### - " + uuu.getPseudo() + " est un admin du cercle qui vient juste d'être modifié");
       	for(Utilisateur uuu : tmp.getUtilisateurs())
    		System.out.println ("### Test Coté cercle ### - " + uuu.getPseudo() + " est un membre du cercle qui vient juste d'être modifié");
		
		
		tmp.setName(paramCercle.getName());
		tmp.setDescription(paramCercle.getDescription());				
			
		cercleRepository.save(tmp);
		
		List<Utilisateur> tmp_admins = tmp.getAdministrateurs();
		List<Utilisateur> tmp_members = tmp.getUtilisateurs();
		
		for(Utilisateur a : tmp_admins)
		{
			List<Cercle> a_cercles_admin = a.getCercles_admin();
			int i=0;
			for( ; i<a_cercles_admin.size() && a_cercles_admin.get(i).getId() != tmp.getId() ; i++);
			
			if (i<a_cercles_admin.size())
			{
				// l'admin actuel était déjà administrateur du cercle avant la modification validée de ce cercle
				a_cercles_admin.remove(i);
				a_cercles_admin.add(tmp);
				a.setCercles_admin(a_cercles_admin);				
			
				
				int j=0;
				for(; j<tmp_previous_admins.size() && !tmp_previous_admins.get(j).getPseudo().equals(a.getPseudo()) ; j++);
				if (j<tmp_previous_admins.size())
				{
					tmp_previous_admins.remove(j);
				}
						
				
			}
			else
			{
				// l'admin actuel n'était pas encore administrateur du cercle avant la modification validée de ce cercle
				a.addCercles_admin(tmp);	
				System.out.println ("### Test Coté user ### - Boucle 1 - Traitement deu nouvel admin " + a.getPseudo());
			}
			userRepository.save(a);	
			
			

		}
		
		for(Utilisateur m : tmp_members)
		{
			List<Cercle> m_cercles_member = m.getCercles_membre();
			int i=0;
			for( ; i<m_cercles_member.size() && m_cercles_member.get(i).getId() != tmp.getId() ; i++);
			if (i<m_cercles_member.size())
			{
				// le membre actuel était déjà membre du cercle avant la modification validée de ce cercle
				m_cercles_member.remove(i);
				m_cercles_member.add(tmp);
				m.setCercles_membre(m_cercles_member);
				
				for(i=0 ; i<tmp_previous_members.size() && !tmp_previous_members.get(i).getPseudo().equals(m.getPseudo()) ; i++);
				if (i<tmp_previous_members.size())
					tmp_previous_members.remove(i);
			}
			else
			{
				// le membre actuel n'était pas encore membre du cercle avant la modification validée de ce cercle
				m.addCercles_membre(tmp);				
			}
			userRepository.save(m);	
		}		
		
		

		
		for(Utilisateur removed_a : tmp_previous_admins)
		{
			System.out.println ("### Test ### - Admin supprimé : " + removed_a.getPseudo());
			
			List<Cercle> removed_a_cercles_admin = removed_a.getCercles_admin();
			int i=0;
			for( ; i<removed_a_cercles_admin.size() && removed_a_cercles_admin.get(i).getId() != tmp.getId() ; i++);
			if (i<removed_a_cercles_admin.size())
			{				
				removed_a_cercles_admin.remove(i);				
				removed_a.setCercles_admin(removed_a_cercles_admin);
			}
			userRepository.save(removed_a);
		}
		
		for(Utilisateur removed_m : tmp_previous_members)
		{
			System.out.println ("### Test ### - Membre supprimé : " + removed_m.getPseudo());
			
			List<Cercle> removed_m_cercles_member = removed_m.getCercles_admin();
			int i=0;
			for( ; i<removed_m_cercles_member.size() && removed_m_cercles_member.get(i).getId() != tmp.getId() ; i++);
			if (i<removed_m_cercles_member.size())
			{				
				removed_m_cercles_member.remove(i);				
				removed_m.setCercles_membre(removed_m_cercles_member);
			}
			userRepository.save(removed_m);
		}		
		
				
		session.removeAttribute("paramcercle");
		session.removeAttribute("paramcercle_previous_admins");
		session.removeAttribute("paramcercle_previous_members");
		
		return "redirect:/user_page";		
	}	
		
		
	// cas de l'annulation de la modification du cercle
	@RequestMapping(value = "/cercle_param_cancel", method = RequestMethod.GET)
	public String requestCercleParamCancel(HttpSession session) {
		
		session.removeAttribute("paramcercle");		
		session.removeAttribute("paramcercle_previous_admins");
		session.removeAttribute("paramcercle_previous_members");
		return "redirect:/user_page";
	}
	
	// accès au paramètre utilisateur à partir de la page de modification de cercle
	// il faut supprimer l'attribut en session
	@RequestMapping(value = "/cercle_param_go_user_param", method = RequestMethod.GET)
	public String requestCercleParamGoUserParam(HttpSession session) {
		
		session.removeAttribute("paramcercle");	
		session.removeAttribute("paramcercle_previous_admins");
		session.removeAttribute("paramcercle_previous_members");
		return "redirect:/user_param";
	}
   
	
	
	// tentative d'accès à la page à partir du bouton-icône "Modifier cercle" dans la liste des cercles de l'utilisateur ( user_page)
	@RequestMapping(value = "/cercle_param_access", method = RequestMethod.GET)
	public String requestCercleParamAccess(@RequestParam(value = "cercle") String cercle_id, HttpSession session, RedirectAttributes redirectAttributes) {
		
		session.removeAttribute("paramcercle");	
		session.removeAttribute("paramcercle_previous_admins");
		session.removeAttribute("paramcercle_previous_members");
		redirectAttributes.addAttribute("cercle", cercle_id);		
		return "redirect:/cercle_param";
	}
	
	
}
