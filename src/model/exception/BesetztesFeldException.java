package model.exception;

@SuppressWarnings("serial")
public class BesetztesFeldException extends LogikException {
	
	public BesetztesFeldException() {
        super("Das Feld ist entweder bereits besetzt oder es gibt keinen Stein zum ziehen oder entfernen.");
    }

}