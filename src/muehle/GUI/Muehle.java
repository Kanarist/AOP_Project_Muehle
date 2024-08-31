package muehle.GUI;

import javax.swing.SwingUtilities;

//Startklasse
public abstract class Muehle {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MuehleFrame());
	}

}
