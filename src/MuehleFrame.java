import javax.swing.JFrame;

import model.Spielbrett;

public class MuehleFrame extends JFrame{

	public MuehleFrame() {
		setTitle("M�hle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1000, 1000);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
		
		
		Spielbrett spielbrett = Spielbrett.initialisiereBrett();
        DrawBoard drawBoard = new DrawBoard(spielbrett);
		add(drawBoard);

		setVisible(true);
		
		
		
	}
	
	
}
