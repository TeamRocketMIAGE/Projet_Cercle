package main;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fichier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private File fileObject;
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	protected Fichier(){
		
	}
	public Fichier(String name, File fileObject) {
		super();
		this.name = name;
		this.fileObject = fileObject;
	}
	
	public long getId() {
		return id;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getFileObject() {
		return fileObject;
	}
	public void setFileObject(File fileObject) {
		this.fileObject = fileObject;
	}
	
	
	
	
}
