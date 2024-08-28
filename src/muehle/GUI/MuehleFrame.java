package muehle.GUI;
import java.awt.Color;

import javax.swing.JFrame;

import model.MuehleMain;

public class MuehleFrame extends JFrame{

	private final MuehleMain muehleMain = new MuehleMain();
	
	public MuehleFrame() {
		setTitle("M�hle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(800, 800);
		getContentPane().setBackground(Color.lightGray); //  need to fix
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
		
		final DrawBoard drawBoard = new DrawBoard(muehleMain);
		muehleMain.setUpdateListener(() -> drawBoard.updateBoard());

		add(drawBoard);

		setVisible(true);
		
	}
}

