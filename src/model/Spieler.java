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

	public void setzeStein(Spielbrett spielbrett, int x, int y) throws BesetztesFeldExeption{
		if (spielbrett.istFeldFrei(x,y)) {
			spielbrett.getFelder()[y][x].setInhalt(Feld.farbeFeld(this));
			this.steine++;
		}
		else {
			throw new BesetztesFeldExeption();
		}
	}
	
	public void bewegeStein(Spielbrett spielbrett, int x1, int y1, int x2, int y2) throws BesetztesFeldExeption,
	NichtBenachbartExeption, FalscheFarbeExeption{
		if (!spielbrett.istFeldFrei(x2,y2) || spielbrett.istFeldFrei(x1,y1)) {
			throw new BesetztesFeldExeption();
		}
		if (!spielbrett.istBenachbart(x1, y1, x2, y2)) {
			throw new NichtBenachbartExeption();
		}
		if(spielbrett.getFelder()[y1][x1].getInhalt() != Feld.farbeFeld(this)) {
			throw new FalscheFarbeExeption(); 
		}
		spielbrett.getFelder()[y2][x2].setInhalt(Feld.farbeFeld(this));
		spielbrett.getFelder()[y1][x1].setInhalt(Feld.Inhalt.leer);

	}
	
	public void springeStein(Spielbrett spielbrett, int x1, int y1, int x2, int y2) throws BesetztesFeldExeption, 
	FalscheFarbeExeption{
		if (!spielbrett.istFeldFrei(x2,y2) || spielbrett.istFeldFrei(x1,y1)) {
			throw new BesetztesFeldExeption();
		}
		if(spielbrett.getFelder()[y1][x1].getInhalt() != Feld.farbeFeld(this)) {
			throw new FalscheFarbeExeption(); 
		}
		spielbrett.getFelder()[y2][x2].setInhalt(Feld.farbeFeld(this));
		spielbrett.getFelder()[y1][x1].setInhalt(Feld.Inhalt.leer);

	}
	
	public void entferneStein(Spielbrett spielbrett, int x, int y, Spieler spieler2) throws BesetztesFeldExeption, 
	FalscheFarbeExeption{
		if (spielbrett.istFeldFrei(x,y)) {
			throw new BesetztesFeldExeption();
		}
		if(spielbrett.getFelder()[y][x].getInhalt() == Feld.farbeFeld(this)) {
			throw new FalscheFarbeExeption(); 
		}
		spielbrett.getFelder()[y][x].setInhalt(Feld.Inhalt.leer);
		spieler2.steine--;

	}
	
}
