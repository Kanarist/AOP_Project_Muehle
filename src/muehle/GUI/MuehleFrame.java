package muehle.GUI;
import java.awt.Dimension;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.MuehleLogik;

public class MuehleFrame extends JFrame{
	
	private static final long serialVersionUID = 4368963525542508317L;
	
	private final MuehleLogik muehleLogik = new MuehleLogik();
	private final DrawBoard drawBoard = new DrawBoard(muehleLogik);
	
	
	public MuehleFrame() {
		setTitle("Mühle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(900, 900));
		
		muehleLogik.setUpdateListener(() -> drawBoard.updateBoard());
		
		add(drawBoard);
		
		initMenu();

		setVisible(true);
	}
	

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu gameMenu = new JMenu("Spiel");
		menuBar.add(gameMenu);
		
		JMenuItem newGameItem = new JMenuItem("Neues Spiel");
		newGameItem.addActionListener(actionEvent -> muehleLogik.neuesSpiel());
		gameMenu.add(newGameItem);
		
		JMenuItem exitItem = new JMenuItem("Beenden");
		exitItem.addActionListener(actionEvent -> {
			this.dispose();
			System.exit(0);
		});
		gameMenu.add(exitItem);
		
		JMenu debugMenu = new JMenu("Debug");
		menuBar.add(debugMenu);
		
		JMenuItem positionItem = new JCheckBoxMenuItem("Position anzeigen");		
		positionItem.addActionListener(actionEvent -> drawBoard.showPostions(positionItem.isSelected()));
		debugMenu.add(positionItem);
	}
	
}
