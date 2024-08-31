package model;

import model.Feld.Inhalt;
import model.exception.VerbotenesFeldException;

public class Spielbrett {

	private Position selPostion;
	
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

	public Position getSelPostion() {
		return selPostion;
	}

	public void setSelPostion(Position selPostion) {
		this.selPostion = selPostion;
	}

	//initialisiert ein leeres Spielbrett
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
	
	//ueberprueft ob Feld leer ist
	public boolean istFeldFrei(int x, int y) {
		if (this.felder[y][x].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
		else if (this.felder[y][x].getInhalt() == Feld.Inhalt.verboten) {
			throw new VerbotenesFeldException();
		}
		else {
			return false;
		}
	}
	
	//ueberprueft ob zwei Felder angrenzend sind, intdem in alle Richtungen geguckt wird, ob das neachste
	//nicht verbotene Feld das zweite Feld ist 
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
				z--;
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
				z--;
			}
			if(z >= 0 && z == y2) {
				return true;
			}
		}
		return false;
	}
	
	//ueberprueft ob Feld Teil einer Muehle ist
	public boolean istTeilVonMuehle(int x, int y) {
		//zaehlt Anzahl weisse und schwarze Felder in x und y Richtung
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

	//ueberprueft ob Steine einer Feldfarbe existieren, die nicht Teil einer Muehle sind
	public boolean freieSteineVorhanden(Inhalt inhalt) {
		for (int y = 0; y < felder.length; y++) {
            for (int x = 0; x < felder[y].length; x++) {
                if(this.felder[y][x].getInhalt() == inhalt) {
                	if(!istTeilVonMuehle(x, y)) {
                		return true;
                	}
                }
            }
        }
		return false;
	}
	
}

