package model;

public class UnremovebleExeption  extends Exception{
	private static final long serialVersionUID = 5643705229050554125L;

	public UnremovebleExeption(){
        super("Der Stein darf nicht entfernt werden.");
    }
}
