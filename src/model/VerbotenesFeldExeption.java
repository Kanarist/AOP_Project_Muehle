package model;

public class VerbotenesFeldExeption extends RuntimeException {
	
	private static final long serialVersionUID = -3084096158706910258L;

	public VerbotenesFeldExeption() {
        super("Auf ein verbotenes Feld darf nicht zugegriffen werden.");
    }

}
