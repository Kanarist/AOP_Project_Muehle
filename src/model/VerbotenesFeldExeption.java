package model;

public class VerbotenesFeldExeption extends RuntimeException {
	
	public VerbotenesFeldExeption() {
        super("Auf ein verbotenes Feld darf nicht zugegriffen werden.");
    }

}
