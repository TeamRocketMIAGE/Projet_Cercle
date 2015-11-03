package main;

// classe utilisée pour la récupération de 2 strings dans un seul objet

public class DoubleString
{
	String value1;
	String value2;
	
	protected DoubleString() {}

	public DoubleString(String value1, String value2) {
		
		this.value1 = value1;
		this.value2 = value2;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}