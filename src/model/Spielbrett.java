package model;

public class Spielbrett {
	private Feld felder[][];
	
	public Feld[][] getFelder() {
		return felder;
	}

	public void setFelder(Feld[][] felder) {
		this.felder = felder;
	}

	private Spielbrett(Feld[][] felder) {
		super();
		this.felder = felder;
	}

	public static Spielbrett initialisiereBrett() {
		Feld[][] felder = new Feld[8][8];
		Feld.Inhalt[][] inhalt = {
				// Definiere den Inhalt für jedes Feld
	            {Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer,
	            Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer},
	            {Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.leer,
	            Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten},
	            {Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.leer,
	            Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten},
	            {Feld.Inhalt.leer, Feld.Inhalt.leer, Feld.Inhalt.leer, Feld.Inhalt.verboten,
	            Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten},
	            {Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten,
	            Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.leer, Feld.Inhalt.leer},
	            {Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten,
	            Feld.Inhalt.leer, Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten},
	            {Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten,
	            Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.leer, Feld.Inhalt.verboten},
	            {Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.verboten,
	            Feld.Inhalt.leer, Feld.Inhalt.verboten, Feld.Inhalt.verboten, Feld.Inhalt.leer}
	        };
        // Setze Inhalt für jedes Feld
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
            	felder[i][j] = new Feld(inhalt[i][j]);
            }
        }
		Spielbrett spielbrett = new Spielbrett(felder);
		return spielbrett;
	}
	
	public boolean istFeldFrei(int x, int y) {
		if (this.felder[y][x].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
		else if (this.felder[y][x].getInhalt() == Feld.Inhalt.verboten) {
			throw new VerbotenesFeldExeption();
		}
		else {
			return false;
		}
	}
	
	public boolean istBenachbart(int x1, int y1, int x2, int y2) {
		if (y1 == y2) {
			int z = x1 + 1;
			while(z < felder.length && this.felder[y1][z].getInhalt() == Feld.Inhalt.verboten) {
				z++;
			}
			if(z < felder.length && z == x2) {
				return true;
			}
			z = x1 - 1;
			while(z >= 0 && this.felder[y1][z].getInhalt() == Feld.Inhalt.verboten) {
				z++;
			}
			if(z >= 0 && z == x2) {
				return true;
			}
		}
		else if (x1 == x2) {
			int z = y1 + 1;
			while(z < felder.length && this.felder[z][x1].getInhalt() == Feld.Inhalt.verboten) {
				z++;
			}
			if(z < felder.length && z == y2) {
				return true;
			}
			z = y1 - 1;
			while(z >= 0 && this.felder[z][x1].getInhalt() == Feld.Inhalt.verboten) {
				z++;
			}
			if(z >= 0 && z == y2) {
				return true;
			}
		}
		return false;
	}
	
	public void setzeStein(Spieler spieler, int x, int y) throws BesetztesFeldExeption{
		if (this.istFeldFrei(x,y)) {
			this.getFelder()[y][x].setInhalt(Feld.farbeFeld(spieler));
			spieler.setSteine(spieler.getSteine() + 1);
		}
		else {
			throw new BesetztesFeldExeption();
		}
	}
	
	public void bewegeStein(int x1, int y1, int x2, int y2) throws BesetztesFeldExeption,
	NichtBenachbartExeption{
		if (!this.istFeldFrei(x2,y2) || this.istFeldFrei(x1,y1)) {
			throw new BesetztesFeldExeption();
		}
		if (!this.istBenachbart(x1, y1, x2, y2)) {
			throw new NichtBenachbartExeption();
		}
		this.getFelder()[y2][x2].setInhalt(this.getFelder()[y1][x1].getInhalt());
		this.getFelder()[y1][x1].setInhalt(Feld.Inhalt.leer);

	}
	
	public void springeStein(int x1, int y1, int x2, int y2) throws BesetztesFeldExeption{
		if (!this.istFeldFrei(x2,y2) || this.istFeldFrei(x1,y1)) {
			throw new BesetztesFeldExeption();
		}
		this.getFelder()[y2][x2].setInhalt(this.getFelder()[x1][y1].getInhalt());;
		this.getFelder()[y1][x1].setInhalt(Feld.Inhalt.leer);

	}
	
	public void entferneStein(Spieler spieler2, int x, int y) throws BesetztesFeldExeption{
		if (this.istFeldFrei(x,y)) {
			throw new BesetztesFeldExeption();
		}
		this.getFelder()[y][x].setInhalt(Feld.Inhalt.leer);
		spieler2.setSteine(spieler2.getSteine() - 1);

	}
	
	public boolean pruefeMuele(int x, int y) throws BesetztesFeldExeption {
		if (this.istFeldFrei(x,y)) {
			throw new BesetztesFeldExeption();
		}
		int w = 0;
		int s = 0;
		for (int i = 0; i < felder.length; i++) {
			if (this.felder[y][i].getInhalt() == Feld.Inhalt.weiss) {
				w++;
			}
			else if (this.felder[y][i].getInhalt() == Feld.Inhalt.schwarz) {
				s++;
			}
		}
			if (w > 2 || s > 2) {
			return true;
		}
		w = 0;
		s = 0;
		for (int i = 0; i < felder.length; i++) {
			if (this.felder[i][x].getInhalt() == Feld.Inhalt.weiss) {
				w++;
			}
			else if (this.felder[i][x].getInhalt() == Feld.Inhalt.schwarz) {
				s++;
			}
		}
			if (w > 2 || s > 2) {
			return true;
		}
		return false;
		
	}
	
	public void BrettAusgeben() {
		for (int i = 0; i < this.getFelder().length; i++) {
            for (int j = 0; j < this.getFelder().length; j++) {
                System.out.printf("%-10s", this.getFelder()[i][j].getInhalt());
            }
            System.out.println();
        }
	}
	
}

