package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;





@Entity
public class Utilisateur implements Serializable  {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String pseudo;
	private String password;
	
	
	@Size(min=2, max=35)
	private String nom;
	
	@Size(min=2, max=35)
	private String prenom;
	
	private String adresse_rue;
	private String adresse_CP;
	private String adresse_ville;
	private String tel;
	private String mail;
	
	
	// liste des contacts de l'utilisateur
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Utilisateur> contact = new ArrayList<Utilisateur>();
	
	
	// liste des utilisateurs qui ont demandé à l'utilisateur actuel de les ajouter dans leurs listes de contacts respectives	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Utilisateur> addRequestContacts = new ArrayList<Utilisateur>();
	

	// liste des cercles dont l'utilisateur est admin (= propriétaire)
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Cercle> cercles_admin = new ArrayList<Cercle>();


	// liste des cercles dont l'utilisateur est seulement membre 
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Cercle> cercles_membre = new ArrayList<Cercle>();



	


	protected Utilisateur()
	{}
	
	
	public Utilisateur(String pseudo, String nom, String prenom, String adresse_rue,
			String adresse_CP, String adresse_ville, String tel, String mail, String password) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse_rue = adresse_rue;
		this.adresse_CP = adresse_CP;
		this.adresse_ville = adresse_ville;
		this.tel = tel;
		this.mail = mail;
		this.setPassword(password);
		 
	}
	
	public Utilisateur(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.nom = "Nom";
		this.prenom = "Prénom";
		this.adresse_rue = "";
		this.adresse_CP = "";
		this.adresse_ville = "";
		this.tel = "";
		this.mail = "";
		this.setPassword(password);
		 
	}
	
	
	public List<Utilisateur> getContact() {
		return contact;
	}


	public void setContact(List<Utilisateur> contact) {
		this.contact = contact;
	}
	
	public void addContact(Utilisateur u) {
		this.contact.add(u);
	}

	

	@Override
	public String toString() {
		return "pseudo=" + pseudo + ", password="
				+ password + ", nom=" + nom + ", prenom=" + prenom
				+ ", adresse_rue=" + adresse_rue + ", adresse_CP=" + adresse_CP
				+ ", adresse_ville=" + adresse_ville + ", tel=" + tel
				+ ", mail=" + mail + "]";
	}

	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
	public List<Utilisateur> getAddRequestContacts() {
		return addRequestContacts;
	}


	public void setAddRequestContacts(List<Utilisateur> addRequestContacts) {
		this.addRequestContacts = addRequestContacts;
	}
	
	public void addRequestNewContact(Utilisateur u) {
		this.addRequestContacts.add(u);
	}
	
	
	public void deleteRequestNewContact(Utilisateur u) {
		
		int i=0;
		for (; i<this.addRequestContacts.size() && !this.addRequestContacts.get(i).getPseudo().equals(u.getPseudo()) ; i++);
		if (i<this.addRequestContacts.size())
		{
			this.addRequestContacts.remove(i);
		}
	}
	
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	

	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getAdresse_rue() {
		return adresse_rue;
	}
	
	public void setAdresse_rue(String adresse_rue) {
		this.adresse_rue = adresse_rue;
	}
	
	public String getAdresse_CP() {
		return adresse_CP;
	}
	
	public void setAdresse_CP(String adresse_CP) {
		this.adresse_CP = adresse_CP;
	}
	
	public String getAdresse_ville() {
		return adresse_ville;
	}
	
	public void setAdresse_ville(String adresse_ville) {
		this.adresse_ville = adresse_ville;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public List<Cercle> getCercles_admin() {
		return cercles_admin;
	}


	public void setCercles_admin(List<Cercle> cercles_admin) {
		this.cercles_admin = cercles_admin;
	}
	
	public void addCercles_admin(Cercle c) {
		this.cercles_admin.add(c);
	}


	public List<Cercle> getCercles_membre() {
		return cercles_membre;
	}


	public void setCercles_membre(List<Cercle> cercles_membre) {
		this.cercles_membre = cercles_membre;
	}
	
	
	public void addCercles_membre(Cercle c) {
		this.cercles_membre.add(c);
	}
	
	public boolean estMembre(Cercle c){
		return(cercles_membre.contains(c));
	}
	
	
	public boolean estAdmin(Cercle c){
		return(cercles_admin.contains(c));
	}

}
