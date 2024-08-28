package muehle.GUI;
import java.awt.Color;

import javax.swing.JFrame;

import model.Spielbrett;

public class MuehleFrame extends JFrame{

	public MuehleFrame() {
		setTitle("Mühle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1000, 1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
		
		
        DrawBoard drawBoard = new DrawBoard();
		add(drawBoard);

		setVisible(true);
		
		
		
	}
	
	
}
