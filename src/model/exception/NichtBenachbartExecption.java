package model.exception;

@SuppressWarnings("serial")
public class NichtBenachbartExecption extends LogikException {

	public NichtBenachbartExecption() {
        super("Die Felder sind keine Nachbarn.");
    }

}