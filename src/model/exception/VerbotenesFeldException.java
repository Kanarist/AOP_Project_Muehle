package model.exception;

public class VerbotenesFeldException extends RuntimeException {
	
	private static final long serialVersionUID = -3084096158706910258L;

	public VerbotenesFeldException() {
        super("Auf ein verbotenes Feld darf nicht zugegriffen werden.");
    }

}
