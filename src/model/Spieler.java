package model;

public class Spieler {
	public enum Farbe {
	    SCHWARZ, WEISS
	}
	private String name;
	private Farbe farbe;
	private int steine;
	
	public Spieler(String name, Farbe farbe, int steine) {
		super();
		this.name = name;
		this.farbe = farbe;
		this.steine = steine;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Farbe getFarbe() {
		return farbe;
	}

	public void setFarbe(Farbe farbe) {
		this.farbe = farbe;
	}

	public int getSteine() {
		return steine;
	}

	public void setSteine(int steine) {
		this.steine = steine;
	}
	
}
