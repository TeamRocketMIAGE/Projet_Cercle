package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;



@Entity
public class Cercle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private int niveau;
	private String description;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Cercle> sousCercles = new ArrayList<Cercle>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Utilisateur> administrateurs = new ArrayList<Utilisateur>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Fichier> fichiers = new ArrayList<Fichier>();
	
	

	public List<Fichier> getFichiers() {
		return fichiers;
	}
	
	public void addFichier(Fichier f){
		this.fichiers.add(f);
	}
	
	
	public Fichier getFichier(int i){
		return fichiers.get(i);
	}
	public void removeFichier(int i){
		this.fichiers.remove(i);
		//attention au décalage des id des fichiers suivant
	}


	protected Cercle(){}
	
	
	public Cercle(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		 
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	
	public List<Cercle> getSousCercles() {
		return sousCercles;
	}
	public void setSousCercles(List<Cercle> sousCercles) {
		this.sousCercles = sousCercles;
	}
	public void addSousCercle(Cercle sousCercle) {
		this.sousCercles.add(sousCercle);
	}
	
	
	public List<Utilisateur> getAdministrateurs() {
		return administrateurs;
	}
	
	public void setAdministrateurs(List<Utilisateur> administrateurs) {
		this.administrateurs = administrateurs;
	}
	
	public void addAdministrateur(Utilisateur u) {
		this.administrateurs.add(u);
	}
	
	public void deleteAdministrateur(Utilisateur u) {
		
		
		int i=0;
		for (; i<this.administrateurs.size() && !this.administrateurs.get(i).getPseudo().equals(u.getPseudo()) ; i++);
		if (i<this.administrateurs.size())
		{
			this.administrateurs.remove(i);
		}
	}
	
	
	
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	public void addUtilisateur(Utilisateur u) {
		this.utilisateurs.add(u);
	}
	
	public void deleteUtilisateur(Utilisateur u) {
		
		
		int i=0;
		for (; i<this.utilisateurs.size() && !this.utilisateurs.get(i).getPseudo().equals(u.getPseudo()) ; i++);
		if (i<this.utilisateurs.size())
		{
			this.utilisateurs.remove(i);
		}
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNiveau() {
		return niveau;
	}
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	
	// renvoie vrai si le pseudo passé en paramètre correspond à un utilisateur admin du cercle
	public boolean isAdministrateur (String pseudo)
	{
		int i=0;
		for (; i<this.administrateurs.size() && !this.administrateurs.get(i).getPseudo().equals(pseudo) ; i++);
		return i<this.administrateurs.size();
	}
	
	// renvoie vrai si le pseudo passé en paramètre correspond à un utilisateur membre du cercle
	public boolean isMembre (String pseudo)
	{
		int i=0;
		for (; i<this.utilisateurs.size() && !this.utilisateurs.get(i).getPseudo().equals(pseudo) ; i++);
		return i<this.utilisateurs.size();
	}
	
	public boolean possedeLeFichier (Fichier f)
	{
		int i=0;
		for (; i<this.fichiers.size() && !this.fichiers.get(i).equals(f) ; i++);
		return i<this.fichiers.size();
	}
	

	
}
