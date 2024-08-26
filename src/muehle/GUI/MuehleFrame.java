package muehle.GUI;
import java.awt.Color;

import javax.swing.JFrame;

import model.Spielbrett;

public class MuehleFrame extends JFrame{

	public MuehleFrame() {
		setTitle("M�hle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(800, 800);
		getContentPane().setBackground(Color.lightGray); //  need to fix
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
		
		
		Spielbrett spielbrett = Spielbrett.initialisiereBrett();
        DrawBoard drawBoard = new DrawBoard(spielbrett);
		add(drawBoard);

		setVisible(true);
		
		
		
	}
}
