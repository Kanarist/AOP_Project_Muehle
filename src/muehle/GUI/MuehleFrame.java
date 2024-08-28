package muehle.GUI;
import java.awt.Color;

import javax.swing.JFrame;

import model.Spielbrett;

public class MuehleFrame extends JFrame{
	// Instanzvariablen deklarieren
    private Spielbrett spielbrett;
    private DrawBoard drawBoard;

	public MuehleFrame() {
		setTitle("M�hle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(800, 800);
		getContentPane().setBackground(Color.lightGray); //  need to fix
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
		
		
		spielbrett = Spielbrett.initialisiereBrett();
        drawBoard = new DrawBoard(spielbrett);
		add(drawBoard);

		setVisible(true);
		
		
		
	}
	public MuehleFrame(Spielbrett brett, DrawBoard board) {
		setTitle("M�hle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(800, 800);
		getContentPane().setBackground(Color.lightGray); //  need to fix
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
		
		
		spielbrett = brett;
        drawBoard = board;
		add(drawBoard);

		setVisible(true);
	}
	
	// Getter f�r spielbrett
    public Spielbrett getSpielbrett() {
        return spielbrett;
    }
 // Getter f�r drawBoard
    public DrawBoard getDrawBoard() {
        return drawBoard;
    }
}
