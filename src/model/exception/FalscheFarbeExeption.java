package model.exception;

@SuppressWarnings("serial")
public class FalscheFarbeExeption extends LogikException {
	
	public FalscheFarbeExeption() {
        super("Der Stein besitzt die falsche Farbe.");
    }

}