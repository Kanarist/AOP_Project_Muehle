package model;

public class Feld {
	public enum Inhalt {
	    schwarz, weiss, leer, verboten
	}
	private Inhalt inhalt;
	
	public Feld(Inhalt inhalt) {
		super();
		this.inhalt = inhalt;
	}
	public Inhalt getInhalt() {
		return inhalt;
	}
	public void setInhalt(Inhalt inhalt) {
		this.inhalt = inhalt;
	}
	
	public static Inhalt farbeFeld(Spieler spieler) {
		if (spieler.getFarbe() == Spieler.Farbe.WEISS) {
			return Feld.Inhalt.weiss;
		}
		else {
			return Feld.Inhalt.schwarz;
		}
	}
	
}
