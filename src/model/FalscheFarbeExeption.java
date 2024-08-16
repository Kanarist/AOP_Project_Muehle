package model;

public class FalscheFarbeExeption extends Exception {
	
	public FalscheFarbeExeption() {
        super("Der Stein besitzt die falsche Farbe.");
    }

}