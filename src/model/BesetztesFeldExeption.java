package model;

@SuppressWarnings("serial")
public class BesetztesFeldExeption extends Exception {
	
	public BesetztesFeldExeption() {
        super("Das Feld ist entweder bereits besetzt oder es gibt keinen Stein zum ziehen oder entfernen.");
    }

}