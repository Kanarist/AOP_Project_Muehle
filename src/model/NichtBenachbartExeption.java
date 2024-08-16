package model;

public class NichtBenachbartExeption extends Exception {
	
	public NichtBenachbartExeption() {
        super("Die Felder sind keine Nachbarn.");
    }

}