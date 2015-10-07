package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;





@Entity
public class Utilisateur implements Serializable  {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String pseudo;
	private String password;
	private String nom;
	private String prenom;
	private String adresse_rue;
	private String adresse_CP;
	private String adresse_ville;
	private String tel;
	private String mail;
	

	

	@OneToMany(fetch=FetchType.LAZY)
	private List<Utilisateur> contact = new ArrayList<Utilisateur>();
	
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
		this.adresse_rue = "Rue";
		this.adresse_CP = "Code postal";
		this.adresse_ville = "Ville";
		this.tel = "0123456789";
		this.mail = "mail@domain.top";
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
	
	
	

}
