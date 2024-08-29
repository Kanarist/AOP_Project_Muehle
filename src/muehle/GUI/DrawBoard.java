package muehle.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.BesetztesFeldExeption;
import model.Feld;
import model.MuehleLogik;
import model.Spielbrett;
import model.Spieler;

// ToDo: fix design of stones
// ToDo: connect of logic

public class DrawBoard extends JPanel implements MouseListener {

	private final MuehleLogik muehleMain;
	private final int squareSize = 650;
	private boolean hasWhiteTurn = false;
	private int maxCountStonesOnBoard = 0;
	private CircleButton[][] buttons = new CircleButton[8][8];
	private CircleButton selectedButton = null;
	private int selectedX = -1;
	private int selectedY = -1;
	private JLabel playerLabel;

	public DrawBoard(MuehleLogik muehleMain) {
		this.muehleMain = muehleMain;
		setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.lightGray);
		playerLabel = createText();
		add(playerLabel);
		updateBoard();
	}

	// create squares and lines of board
	public void createSquaresAndLines(Graphics g) {
		Graphics2D lineAndSquare = (Graphics2D) g;

		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int newSquareSize;

		for (int i = 0; i < 3; i++) {
			lineAndSquare.setStroke(new BasicStroke(5));
			newSquareSize = squareSize - i * 200;
			int x = (panelWidth - newSquareSize) / 2;
			int y = (panelHeight - newSquareSize) / 2;
			lineAndSquare.setColor(Color.BLACK);
			lineAndSquare.drawRect(x, y, newSquareSize, newSquareSize);

			// draw lines
			if (i == 0) {

				// vertical lines
				lineAndSquare.drawLine(panelWidth / 2, y, panelWidth / 2, (panelHeight - squareSize + 2 * 200) / 2); // top
				lineAndSquare.drawLine(panelWidth / 2, (panelHeight + newSquareSize) / 2, panelWidth / 2,
						((panelHeight + panelHeight - squareSize + 2 * 200) / 2)
								- (panelHeight - squareSize + 2 * 200) / 2 + 125); // bottom

				// horizontal lines
				lineAndSquare.drawLine(x, panelHeight / 2, (panelWidth - squareSize + 2 * 200) / 2, panelHeight / 2); // left
				lineAndSquare.drawLine((panelWidth + newSquareSize) / 2, panelHeight / 2,
						(panelWidth + panelWidth - squareSize + 2 * 200) / 2 - (panelWidth - squareSize + 2 * 200) / 2
								+ 125,
						panelHeight / 2); // reight

			}
		}
	}

	// create of circles
	private void createFields() {
		int var = 200;
	    int panelWidth = getWidth();
	    int panelHeight = getHeight();
	    int x = (panelWidth - squareSize) / 2;
	    int y = (panelHeight - squareSize) / 2;
	    int x1 = (panelWidth - squareSize+var) / 2;
	    int y1 = (panelHeight - squareSize+var) / 2;
	    int x2 = (panelWidth - squareSize+2*var) / 2;
	    int y2 = (panelHeight - squareSize+2*var) / 2;
	    int buttonDiameter = new CircleButton().getCircleDiameter();
	    int[][][] circles = new int[8][8][2]; 
	    
	    for(int i = 0; i < 8; i++) {
		    for(int j = 0; j < 8; j++) {
		    	if(i == 0) {
		    		switch(j) {
				    case 0 : circles[i][j] = new int[]{ x - buttonDiameter / 2, y - buttonDiameter / 2};break;
				    case 3 : circles[i][j] = new int[]{ x - buttonDiameter / 2, y + squareSize / 2 - buttonDiameter / 2 };break;
				    case 7 : circles[i][j] = new int[]{ x - buttonDiameter / 2, y + squareSize - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 1) {
		    		switch(j) {
				    case 1 : circles[i][j] = new int[]{ x1 - buttonDiameter / 2, y1 - buttonDiameter / 2 };break;
				    case 3 : circles[i][j] = new int[]{ x1 - buttonDiameter / 2, y1 + (squareSize-var) / 2 - buttonDiameter / 2 };break;
				    case 6 : circles[i][j] = new int[]{ x1 - buttonDiameter / 2, y1 + (squareSize-var) - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 2) {
		    		switch(j) {
				    case 2 : circles[i][j] = new int[]{ x2 - buttonDiameter / 2, y2 - buttonDiameter / 2 };break;
				    case 3 : circles[i][j] = new int[]{ x2 - buttonDiameter / 2, y1 + (squareSize-var) / 2 - buttonDiameter / 2 };break;
				    case 5 : circles[i][j] = new int[]{ x2 - buttonDiameter / 2, y2 + (squareSize-2*var) - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 3) {
		    		switch(j) {
				    case 0 : circles[i][j] = new int[]{ x + squareSize / 2 - buttonDiameter / 2, y - buttonDiameter / 2 };break;
				    case 1 : circles[i][j] = new int[]{ x + squareSize / 2 - buttonDiameter / 2, y1 - buttonDiameter / 2 };break;
				    case 2 : circles[i][j] = new int[]{ x + squareSize / 2 - buttonDiameter / 2, y2 - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 4) {
		    		switch(j) {
				    case 5 : circles[i][j] = new int[]{ x2 + (squareSize-2*var) / 2 - buttonDiameter / 2, y2 + (squareSize-2*var) - buttonDiameter / 2 };break;
				    case 6 : circles[i][j] = new int[]{ x1 + (squareSize-var) / 2 - buttonDiameter / 2, y1 + (squareSize-var) - buttonDiameter / 2 };break;
				    case 7 : circles[i][j] = new int[]{ x + squareSize / 2 - buttonDiameter / 2, y + squareSize - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 5) {
		    		switch(j) {
				    case 2 : circles[i][j] = new int[]{ x2 + (squareSize-2*var) - buttonDiameter / 2, y2 - buttonDiameter / 2 };break;
				    case 4 : circles[i][j] = new int[]{ x2 + (squareSize-2*var) - buttonDiameter / 2, y + squareSize / 2 - buttonDiameter / 2};break;
				    case 5 : circles[i][j] = new int[]{ x2 + (squareSize-2*var) - buttonDiameter / 2, y2 + (squareSize-2*var) - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 6) {
		    		switch(j) {
				    case 1 : circles[i][j] = new int[]{ x1 + (squareSize-var) - buttonDiameter / 2, y1 - buttonDiameter / 2 };break;
				    case 4 : circles[i][j] = new int[]{ x1 + (squareSize-var) - buttonDiameter / 2, y1 + (squareSize-var) / 2 - buttonDiameter / 2 };break;
				    case 6 : circles[i][j] = new int[]{ x1 + (squareSize-var) - buttonDiameter / 2, y1 + (squareSize-var) - buttonDiameter / 2 };break;
				    }
		    	}else if(i == 7) {
		    		switch(j) {
				    case 0 : circles[i][j] = new int[]{ x + squareSize - buttonDiameter / 2, y - buttonDiameter / 2 };break;
				    case 4 : circles[i][j] = new int[]{ x + squareSize - buttonDiameter / 2, y + squareSize / 2 - buttonDiameter / 2 };break;
				    case 7 : circles[i][j] = new int[]{ x + squareSize - buttonDiameter / 2, y + squareSize - buttonDiameter / 2 };break;
				    }
		    	}
		    	
		    }	
	    }
	    
	 
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (muehleMain.getSpielbrett().getFelder()[i][j].getInhalt() != Feld.Inhalt.verboten) {
	                CircleButton button = new CircleButton();
	                button.setBounds(circles[j][i][0], circles[j][i][1], buttonDiameter, buttonDiameter);
	                button.setEnabled(false);
	                button.setBackground(Color.gray); // Test color
	                button.addMouseListener(this);
	                buttons[i][j] = button;
	                add(button);
	            }
	        }
	    }
	}

	public void paintComponent(Graphics g) {
		removeAll();
		super.paintComponent(g);
		createSquaresAndLines(g);
		createFields();
	}

	public JLabel createText() {
		JLabel label = new JLabel(hasWhiteTurn ? "Spieler Schwarz ist dran" : "Spieler Weiß ist dran", JLabel.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		return label;
	}

	public void updateBoard() {
		Feld[][] felder = muehleMain.getSpielbrett().getFelder();

		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[i].length; j++) {
				CircleButton button = buttons[i][j];
				if (button != null) {
					Feld.Inhalt inhalt = felder[i][j].getInhalt();
					if (inhalt == Feld.Inhalt.weiss) {
						button.setBackground(Color.WHITE);
						button.setEnabled(true);
					} else if (inhalt == Feld.Inhalt.schwarz) {
						button.setBackground(Color.BLACK);
						button.setEnabled(true);
					} else if (inhalt == Feld.Inhalt.verboten) {
						button.setEnabled(false);
					} else {
						button.setBackground(Color.gray);
						button.setEnabled(true);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		final Spielbrett spielbrett = muehleMain.getSpielbrett();

		// Phase 1
		if (e.getSource() instanceof CircleButton && maxCountStonesOnBoard < 18) {
			CircleButton button = (CircleButton) e.getSource();

			Position position = getPosition4Button(button);
			if(position == null) {
				return;
			}
			int x = position.getXAxis();
			int y = position.getYAxis();

			// verarbeitet den Klick
			
			if (spielbrett.istFeldFrei(x, y)) {
				System.out.println("Button geklickt bei Position: (" + x + ", " + y + ")"); // TEst

				Spieler currentPlayer = hasWhiteTurn ? muehleMain.getSpieler1() : muehleMain.getSpieler2();
				try {
					spielbrett.setzeStein(currentPlayer, x, y);
				} catch (BesetztesFeldExeption e1) {
					e1.printStackTrace();
				}
				Stone stone = new Stone(hasWhiteTurn);
				hasWhiteTurn = hasWhiteTurn ? false : true;
				button.add(stone);
				stone.setBounds(-15, -15, stone.getStoneRadius() * 2, stone.getStoneRadius() * 2);
				System.out.println("Button pressed!"); // TEST

				try {
					spielbrett.pruefeMuele(x, y);
				} catch (BesetztesFeldExeption e1) {
					e1.printStackTrace();
				}
				maxCountStonesOnBoard++;
			}

			// Phase 2
		} else {
			CircleButton button = (CircleButton) e.getSource();

			Position position = getPosition4Button(button);
			if(position == null) {
				return;
			}
			int x = position.getXAxis();
			int y = position.getYAxis();

			if (selectedButton == null) {
				if (spielbrett.getFelder()[y][x]
						.getInhalt() == (hasWhiteTurn ? Feld.Inhalt.weiss : Feld.Inhalt.schwarz)) {
					// select the stone
					selectedButton = button;
					selectedX = x;
					selectedY = y;
					System.out.println("Stein ausgewählt bei Position: (" + x + ", " + y + ")");
				} else {
					System.out.println("Kein gültiger Stein zum Auswählen!(" + x + ", " + y + ")");
				}
			} else {
				if (selectedButton == button) {
					selectedButton = null;
					selectedX = -1;
					selectedY = -1;
					System.out.println("Stein-Auswahl aufgehoben.");
				} else {
					// neuer Button wurde geklickt
					try {
						if (spielbrett.istFeldFrei(x, y)) {
							spielbrett.bewegeStein(selectedX, selectedY, x, y);

							updateBoard();

							selectedButton = null;
							selectedX = -1;
							selectedY = -1;

							hasWhiteTurn = !hasWhiteTurn;
						} else {
							System.out.println("Ziel-Feld ist nicht frei! (" + x + ", " + y + ")");
						}
					} catch (Exception ex) {
						System.out.println("Fehler beim Bewegen des Steins: " + ex.getMessage());
					}

				}
			}
		}
	}
	
	private Position getPosition4Button(CircleButton button) {
		for (int y = 0; y < buttons.length; y++) {
			for (int x = 0; x < buttons[y].length; x++) {
				if (buttons[y][x] == button) {
					return new Position(x, y);
				}
			}
		}
		return null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof CircleButton) {
			CircleButton button = (CircleButton) e.getSource();
//	        System.out.println("Cursor is on Button!");	// TEST
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof CircleButton) {
			CircleButton button = (CircleButton) e.getSource();
//	        System.out.println("Cursor NOT is on Button!");	// TEST
		}

	}

	class RoundedBorder implements Border {

		private int radius;
		private Color color;

		RoundedBorder(Color color, int radius) {
			this.radius = radius;
			this.color = color;

		}

		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(color);
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}
	}

}
