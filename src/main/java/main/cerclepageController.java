package main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class cerclepageController {

	@Autowired
	CercleRepository cercleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FichierRepository fichierRepository;
	
    @Autowired 
    ChatMessageRepository chatMessagesRepository;

	@RequestMapping(value = "/cercle_page", method = RequestMethod.GET)
	public String requestCreatePageCercle(@RequestParam(value = "cercle") String cercle_id, Model model,
			RedirectAttributes redirectAttributes) {

		System.out.println("Cercle actuel : " + cercle_id);

		Cercle currentCercle = cercleRepository.findById(Long.parseLong(cercle_id));

		if (currentCercle != null) {
			// obtention de l'id de l'utilisateur connecté
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String currentUserPseudo = auth.getName();

			// vérification que l'user connecté a droit d'accéder au cercle
			// demandé
			if (currentCercle.isMembre(currentUserPseudo) || currentCercle.isAdministrateur(currentUserPseudo)) {
				Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);

				/*
				 * ajout des données nécessaires à transmettre à la page
				 */
				model.addAttribute("currentCercle", currentCercle);

				/*
				 * Informations relatives aux contacts à transmettre à la page
				 */

				// ajout de la liste des membres du cercle
				model.addAttribute("cercle_membres", currentCercle.getUtilisateurs());

				// ajout de la liste des administrateurs du cercle
				model.addAttribute("cercle_admins", currentCercle.getAdministrateurs());

				// ajout des contacts de l'utilisateur actuel dans la requête
				model.addAttribute("contacts", currentUser.getContact());

				SimpleString user_added = new SimpleString("");
				// ajout de la variable qui va permettre de stocker le pseudo
				// d'un utilisateur à ajouter dans les contacts
				model.addAttribute("user_added", user_added);

				// ajout de la liste des demandes extérieures d'ajout à la liste
				// des contacts de l'utilisateur connecté
				model.addAttribute("new_contact_to_confirm", currentUser.getAddRequestContacts());

				// ajout de la liste des fichiers du cercle
				model.addAttribute("cercle_fichiers", currentCercle.getFichiers());

				return "cercle_page";
			} else {
				redirectAttributes.addAttribute("error", "unauthorized_cercle");
			}

		} else {
			redirectAttributes.addAttribute("error", "cercle_does_not_exist");
		}

		// s'il y a un soucis quelque part, renvoyer vers l'espace utilisateur
		return ("redirect:/user_page");
	}

	@RequestMapping(value = "/cercle_page/adduser", method = RequestMethod.POST)
	public String addUserRequest(SimpleString user_added, RedirectAttributes redirectAttributes,
			@RequestParam(value = "cercle") String cercle_id) {

		System.out.println("Demande d'ajout de l'utilisateur suivant : " + user_added.value);

		redirectAttributes.addAttribute("cercle", cercle_id);

		if (userRepository.findByPseudo(user_added.value) == null) {
			System.out.println("L'utilisateur " + user_added.value + " n'existe pas dans la base de données.");
			redirectAttributes.addAttribute("add_request", "user_does_not_exist");
		} else {
			// obtention de l'id de l'utilisateur connecté
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String currentUserPseudo = auth.getName();

			if (currentUserPseudo.equals(user_added.value)) {
				redirectAttributes.addAttribute("add_request", "you_cannot_add_yourself");
			} else {
				Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);
				int i = 0;
				for (; i < currentUser.getContact().size()
						&& !currentUser.getContact().get(i).getPseudo().equals(user_added.value); i++)
					;
				if (i < currentUser.getContact().size()) {
					redirectAttributes.addAttribute("add_request", "user_already_in_list");
				} else {
					Utilisateur askedUser = (Utilisateur) userRepository.findByPseudo(user_added.value);
					i = 0;
					for (; i < askedUser.getAddRequestContacts().size()
							&& askedUser.getAddRequestContacts().get(i).getPseudo().equals(currentUserPseudo); i++)
						;
					if (i < askedUser.getAddRequestContacts().size()) {
						redirectAttributes.addAttribute("add_request", "user_already_requested");
					} else {
						// tout est ok, il faut ajouter la demande dans la liste
						// des demandes de user_added
						askedUser.addRequestNewContact(currentUser);
						userRepository.save(askedUser);
						redirectAttributes.addAttribute("add_request", "ok");

					}
				}
			}

		}

		return "redirect:/cercle_page";

	}

	@RequestMapping(value = "/cercle_page/confirm_adduser/{pseudo}", method = RequestMethod.POST, params = "confirm_add=Accepter")
	public String confirmAddUserRequestt(@PathVariable("pseudo") String user_added_confirmed,
			RedirectAttributes redirectAttributes, @RequestParam(value = "cercle") String cercle_id) {

		System.out.println("Confirmation de la demande d'ajout de l'utilisateur " + user_added_confirmed + ".");

		redirectAttributes.addAttribute("cercle", cercle_id);

		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();

		// ajout du nouvel utilisateur dans sa liste de contact
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);
		Utilisateur askingUser = (Utilisateur) userRepository.findByPseudo(user_added_confirmed);
		currentUser.addContact(askingUser);
		currentUser.deleteRequestNewContact(askingUser);
		userRepository.save(currentUser);

		askingUser.addContact(currentUser);
		userRepository.save(askingUser);

		return "redirect:/cercle_page";
	}

	@RequestMapping(value = "/cercle_page/confirm_adduser/{pseudo}", method = RequestMethod.POST, params = "confirm_add=Refuser")
	public String refuseAddUserRequestt(@PathVariable("pseudo") String user_added_confirmed,
			RedirectAttributes redirectAttributes, @RequestParam(value = "cercle") String cercle_id) {

		System.out.println("Refus de la demande d'ajout.");

		redirectAttributes.addAttribute("cercle", cercle_id);

		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();

		// il faut enlever le user qui a demandé l'ajout de la lise des
		// utilisateurs qui nous ont demandé de nous ajouter
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);
		Utilisateur askingUser = (Utilisateur) userRepository.findByPseudo(user_added_confirmed);
		currentUser.deleteRequestNewContact(askingUser);
		userRepository.save(currentUser);

		return "redirect:/cercle_page";
	}

	/*
	 * La suite du code permet de gérer les fichiers du cercle
	 */

	@RequestMapping(value = "/cercle_page/upload{cercleid}", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @RequestParam("cercleid") String cercle_id) {

		Cercle currentCercle = cercleRepository.findById(Long.parseLong(cercle_id));

		// vérification des droits
		// obtention de l'id de l'utilisateur connecté
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);

		if (currentUser.estAdmin(currentCercle) || currentUser.estMembre(currentCercle)) {

			if (!file.isEmpty()) {
				try {
					// on sauvegarde le fichier
					byte[] bytes = file.getBytes();

					File f = new File(file.getOriginalFilename());
					FileOutputStream fos = new FileOutputStream(f);
					BufferedOutputStream stream = new BufferedOutputStream(fos);
					stream.write(bytes);
					stream.close();
					fos.close();

					Fichier fichier = new Fichier(name, f);
					fichier.setDescription(description);
					currentCercle.addFichier(fichier);

					fichierRepository.save(fichier);
					cercleRepository.save(currentCercle);

					redirectAttributes.addAttribute("cercle", cercle_id);
					return "redirect:/cercle_page";

					
				} catch (Exception e) {
					return "Echec de l'upload du fichier" + name + " => " + e.getMessage();
				}
			} else {
				return "Le fichier " + name + " est vide.";
			}
		}
		else
			return "redirect:/cercle_page";

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFichier(RedirectAttributes redirectAttributes,
			@RequestParam(value = "fichier") String fichier_id, @RequestParam(value = "cercle") String cercle_id,
			HttpServletResponse response) throws IOException {

		//System.out.println("tentative de téléchargement d'un fichier par un client");

		// vérification des droits
		// obtention de l'id de l'utilisateur connecté
		Cercle currentCercle = cercleRepository.findById(Long.parseLong(cercle_id));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);

		Fichier fichier = (Fichier) fichierRepository.findById(Long.parseLong(fichier_id));
		File f = fichier.getFileObject();
		if ((currentUser.estAdmin(currentCercle) || currentUser.estMembre(currentCercle))
				& (currentCercle.possedeLeFichier(fichier))) {
			InputStream is = new FileInputStream(f);

			response.setContentType("application/octet-stream");
			response.setContentLength((int) f.length());

			// Reponse header
			response.setHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();

		}
		//redirectAttributes.addAttribute("cercle", cercle_id);
		//return null;//"redirect:/cercle_page";
	}
	
	@RequestMapping(value = "/deletefichier", method = RequestMethod.GET)
	public String deleteFichier(RedirectAttributes redirectAttributes,
			@RequestParam(value = "fichier") String fichier_id, @RequestParam(value = "cercle") String cercle_id,
			HttpServletResponse response) throws IOException {

		// vérification des droits
		// obtention de l'id de l'utilisateur connecté
		Cercle currentCercle = cercleRepository.findById(Long.parseLong(cercle_id));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserPseudo = auth.getName();
		Utilisateur currentUser = (Utilisateur) userRepository.findByPseudo(currentUserPseudo);

		Fichier fichier = (Fichier) fichierRepository.findById(Long.parseLong(fichier_id));
		File f = fichier.getFileObject();
		if ((currentUser.estAdmin(currentCercle) || currentUser.estMembre(currentCercle))
				& (currentCercle.possedeLeFichier(fichier))) {
			//ON DELETE
			currentCercle.removeFichier(fichier);
			cercleRepository.save(currentCercle);
		}
		redirectAttributes.addAttribute("cercle", cercle_id);

		return "redirect:/cercle_page";
	}

}
