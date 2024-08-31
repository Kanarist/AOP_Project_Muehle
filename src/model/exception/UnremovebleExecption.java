package model.exception;

public class UnremovebleExecption  extends LogikException {
	private static final long serialVersionUID = 5643705229050554125L;

	public UnremovebleExecption(){
        super("Der Stein darf nicht entfernt werden.");
    }
}
