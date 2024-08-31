package muehle.GUI;
import java.awt.Dimension;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.MuehleLogik;

//Hauptfenster
public class MuehleFrame extends JFrame{
	
	private static final long serialVersionUID = 4368963525542508317L;
	
	private final MuehleLogik muehleLogik = new MuehleLogik();
	private final DrawBoard drawBoard = new DrawBoard(muehleLogik);
	
	
	public MuehleFrame() {
		setTitle("Mühle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(900, 900));
		
		//Registrierung Spielbrettoberflaeche in Logik fuer Aktualisierungen
		muehleLogik.setUpdateListener(() -> drawBoard.updateBoard());
		
		add(drawBoard);
		
		initMenu();

		setVisible(true);
	}
	

	//Spielmenues
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
		
		JCheckBoxMenuItem positionItem = new JCheckBoxMenuItem("Position anzeigen");		
		positionItem.addActionListener(actionEvent -> drawBoard.showGameCoordinates(positionItem.isSelected()));
		debugMenu.add(positionItem);
		
		JMenuItem changePlayerItem = new JMenuItem("Wechsel Spieler");
		changePlayerItem.addActionListener(actionEvent -> muehleLogik.debugChangePlayer());
		debugMenu.add(changePlayerItem);
		
		JMenuItem forceMovePhase = new JMenuItem("Setzphase überspringen");
		forceMovePhase.addActionListener(actionEvent -> muehleLogik.debugForceMovePhase());
		debugMenu.add(forceMovePhase);
		
		JCheckBoxMenuItem placeStoneItem = new JCheckBoxMenuItem("Kontextmenü Steine");
		placeStoneItem.addActionListener(actionEvent -> drawBoard.setShowStoneMenu(placeStoneItem.isSelected()));
		debugMenu.add(placeStoneItem);
		
		JCheckBoxMenuItem debugMessageItem = new JCheckBoxMenuItem("Debug Messages");		
		debugMessageItem.addActionListener(actionEvent -> drawBoard.setShowDebugMessage(debugMessageItem.isSelected()));
		debugMenu.add(debugMessageItem);
	}
	
}
