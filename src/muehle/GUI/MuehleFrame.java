package muehle.GUI;
import javax.swing.JFrame;

import model.MuehleLogik;

public class MuehleFrame extends JFrame{
	
	private static final long serialVersionUID = 4368963525542508317L;
	
	private final MuehleLogik muehleMain = new MuehleLogik();

	public MuehleFrame() {
		setTitle("Mühle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1000, 1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
		
		
        final DrawBoard drawBoard = new DrawBoard(muehleMain);
		muehleMain.setUpdateListener(() -> drawBoard.updateBoard());
		
		add(drawBoard);

		setVisible(true);
		
		
		
	}
	
	
}
