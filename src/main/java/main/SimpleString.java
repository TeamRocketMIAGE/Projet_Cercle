package main;

// cette classe est créée car thymeleaf ne permet que de passer des objets et ensuite d'accéder à leur attribut,
// plutôt d'accéder directement à un attribut (des String) sans passer par un objet
// Cet objet simple permet donc de passer une simple String (moins lourd) au lieu d'un objet complet
public class SimpleString {

	String value;
	
	protected SimpleString() {}

	public SimpleString(String value) {
		
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
