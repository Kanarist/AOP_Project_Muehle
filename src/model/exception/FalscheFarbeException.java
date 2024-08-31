package model.exception;

@SuppressWarnings("serial")
public class FalscheFarbeException extends LogikException {
	
	public FalscheFarbeException() {
        super("Der Stein besitzt die falsche Farbe.");
    }

}