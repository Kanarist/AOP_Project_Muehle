package model.exception;

@SuppressWarnings("serial")
public class NichtBenachbartExeption extends LogikException {

	public NichtBenachbartExeption() {
        super("Die Felder sind keine Nachbarn.");
    }

}