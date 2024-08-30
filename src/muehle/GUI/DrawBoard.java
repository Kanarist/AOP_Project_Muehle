package muehle.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import model.Feld;
import model.MuehleLogik;
import model.Position;
import model.Spieler.Farbe;

public class DrawBoard extends JPanel implements MouseListener {

	private static final long serialVersionUID = -4262598812591836725L;

	private final MuehleLogik muehleLogik;
	private final int squareSize = 650;
	private final CircleButton[][] buttons = new CircleButton[8][8];
	private final JLabel playerLabel = new JLabel("");
	private final JLabel debugMessageLabel = new JLabel("");
	private boolean showStoneMenu;

	public DrawBoard(MuehleLogik muehleLogik) {
		this.muehleLogik = muehleLogik;
		setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.lightGray);

		playerLabel.setForeground(Color.BLACK);
		playerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		add(playerLabel);
		
		debugMessageLabel.setForeground(Color.RED);
		debugMessageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		debugMessageLabel.setVisible(false);
		add(debugMessageLabel);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (muehleLogik.getFelder()[i][j].getInhalt() != Feld.Inhalt.verboten) {
					CircleButton button = new CircleButton();
					button.setEnabled(false);
					button.setBackground(Color.gray);
					button.setBorderPainted(false);
					button.addMouseListener(this);
					buttons[i][j] = button;
					add(button);
				}
			}
		}

		updatePlayerLabel();
	}

	public void setShowStoneMenu(boolean showStoneMenu) {
		this.showStoneMenu = showStoneMenu;
	}
	
	public void setShowDebugMessage(boolean showDebugMessage) {
		debugMessageLabel.setVisible(showDebugMessage);
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
	private void paintFields() {

		int var = 200;
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int x = (panelWidth - squareSize) / 2;
		int y = (panelHeight - squareSize) / 2;
		int x1 = (panelWidth - squareSize + var) / 2;
		int y1 = (panelHeight - squareSize + var) / 2;
		int x2 = (panelWidth - squareSize + 2 * var) / 2;
		int y2 = (panelHeight - squareSize + 2 * var) / 2;
		int buttonDiameter = new CircleButton().getCircleDiameter();
		int[][][] circles = new int[8][8][2];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == 0) {
					switch (j) {
					case 0:
						circles[i][j] = new int[] { x - buttonDiameter / 2, y - buttonDiameter / 2 };
						break;
					case 3:
						circles[i][j] = new int[] { x - buttonDiameter / 2, y + squareSize / 2 - buttonDiameter / 2 };
						break;
					case 7:
						circles[i][j] = new int[] { x - buttonDiameter / 2, y + squareSize - buttonDiameter / 2 };
						break;
					}
				} else if (i == 1) {
					switch (j) {
					case 1:
						circles[i][j] = new int[] { x1 - buttonDiameter / 2, y1 - buttonDiameter / 2 };
						break;
					case 3:
						circles[i][j] = new int[] { x1 - buttonDiameter / 2,
								y1 + (squareSize - var) / 2 - buttonDiameter / 2 };
						break;
					case 6:
						circles[i][j] = new int[] { x1 - buttonDiameter / 2,
								y1 + (squareSize - var) - buttonDiameter / 2 };
						break;
					}
				} else if (i == 2) {
					switch (j) {
					case 2:
						circles[i][j] = new int[] { x2 - buttonDiameter / 2, y2 - buttonDiameter / 2 };
						break;
					case 3:
						circles[i][j] = new int[] { x2 - buttonDiameter / 2,
								y1 + (squareSize - var) / 2 - buttonDiameter / 2 };
						break;
					case 5:
						circles[i][j] = new int[] { x2 - buttonDiameter / 2,
								y2 + (squareSize - 2 * var) - buttonDiameter / 2 };
						break;
					}
				} else if (i == 3) {
					switch (j) {
					case 0:
						circles[i][j] = new int[] { x + squareSize / 2 - buttonDiameter / 2, y - buttonDiameter / 2 };
						break;
					case 1:
						circles[i][j] = new int[] { x + squareSize / 2 - buttonDiameter / 2, y1 - buttonDiameter / 2 };
						break;
					case 2:
						circles[i][j] = new int[] { x + squareSize / 2 - buttonDiameter / 2, y2 - buttonDiameter / 2 };
						break;
					}
				} else if (i == 4) {
					switch (j) {
					case 5:
						circles[i][j] = new int[] { x2 + (squareSize - 2 * var) / 2 - buttonDiameter / 2,
								y2 + (squareSize - 2 * var) - buttonDiameter / 2 };
						break;
					case 6:
						circles[i][j] = new int[] { x1 + (squareSize - var) / 2 - buttonDiameter / 2,
								y1 + (squareSize - var) - buttonDiameter / 2 };
						break;
					case 7:
						circles[i][j] = new int[] { x + squareSize / 2 - buttonDiameter / 2,
								y + squareSize - buttonDiameter / 2 };
						break;
					}
				} else if (i == 5) {
					switch (j) {
					case 2:
						circles[i][j] = new int[] { x2 + (squareSize - 2 * var) - buttonDiameter / 2,
								y2 - buttonDiameter / 2 };
						break;
					case 4:
						circles[i][j] = new int[] { x2 + (squareSize - 2 * var) - buttonDiameter / 2,
								y + squareSize / 2 - buttonDiameter / 2 };
						break;
					case 5:
						circles[i][j] = new int[] { x2 + (squareSize - 2 * var) - buttonDiameter / 2,
								y2 + (squareSize - 2 * var) - buttonDiameter / 2 };
						break;
					}
				} else if (i == 6) {
					switch (j) {
					case 1:
						circles[i][j] = new int[] { x1 + (squareSize - var) - buttonDiameter / 2,
								y1 - buttonDiameter / 2 };
						break;
					case 4:
						circles[i][j] = new int[] { x1 + (squareSize - var) - buttonDiameter / 2,
								y1 + (squareSize - var) / 2 - buttonDiameter / 2 };
						break;
					case 6:
						circles[i][j] = new int[] { x1 + (squareSize - var) - buttonDiameter / 2,
								y1 + (squareSize - var) - buttonDiameter / 2 };
						break;
					}
				} else if (i == 7) {
					switch (j) {
					case 0:
						circles[i][j] = new int[] { x + squareSize - buttonDiameter / 2, y - buttonDiameter / 2 };
						break;
					case 4:
						circles[i][j] = new int[] { x + squareSize - buttonDiameter / 2,
								y + squareSize / 2 - buttonDiameter / 2 };
						break;
					case 7:
						circles[i][j] = new int[] { x + squareSize - buttonDiameter / 2,
								y + squareSize - buttonDiameter / 2 };
						break;
					}
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				CircleButton button = buttons[i][j];
				if (button != null) {
					button.setBounds(circles[j][i][0], circles[j][i][1], buttonDiameter, buttonDiameter);
				}
			}
		}

		playerLabel.setBounds(10, 10, panelWidth - 20, 40);

		debugMessageLabel.setBounds(10, panelHeight - 40, panelWidth - 20, 30);
	}

	public void showPostions(boolean show) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				CircleButton button = buttons[i][j];
				if (button != null) {
					button.setText(show ? j + "/" + i : "");
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		createSquaresAndLines(g);
		paintFields();
	}

	private void updatePlayerLabel() {

		String text;

		if (muehleLogik.isGameOver()) {
			text = "Spiel vorbei! " + muehleLogik.getWinner().getName() + " hat gewonnen!";
		} else {
			text = String.format("Spieler %s ist dran: ",
					muehleLogik.getCurrentPlayer().getFarbe() == Farbe.SCHWARZ ? "Schwarz" : "Weiß");

			if (muehleLogik.isRemoveStoneStatus()) {
				text += "!!! M Ü H L E !!! Nimm einen Stein vom Gegner!";
			} else if (muehleLogik.isSetPhase()) {
				text += "Setze einen Stein!";
			} else {
				text += "Ziehe einen Stein!";
			}
		}

		playerLabel.setText(text);
	}

	public void updateBoard() {
		final Feld[][] felder = muehleLogik.getFelder();
		EventQueue.invokeLater(() -> {
			for (int y = 0; y < felder.length; y++) {
				for (int x = 0; x < felder[y].length; x++) {
					CircleButton button = buttons[y][x];
					if (button != null) {
						button.removeAll();
						Feld.Inhalt inhalt = felder[y][x].getInhalt();
						if (inhalt == Feld.Inhalt.weiss) {
							button.setBackground(muehleLogik.isSelPosition(x, y) ? Color.YELLOW : Color.WHITE);
							button.setEnabled(true);
						} else if (inhalt == Feld.Inhalt.schwarz) {
							button.setBackground(muehleLogik.isSelPosition(x, y) ? Color.CYAN : Color.BLACK);
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
		});
		updatePlayerLabel();
		
		
		String debugMessage = muehleLogik.getDebugMessage();
		debugMessageLabel.setText(debugMessage != null ? debugMessage : "");
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

	private void showContextMenu4Button(int clickPosX, int clickPosY, CircleButton button) {
		Position position = getPosition4Button(button);
		if (position != null) {
			JPopupMenu contextMenu = new JPopupMenu();
			JMenuItem setzeLeer = new JMenuItem("Stein entfernen");
			setzeLeer.addActionListener(actionEvent -> muehleLogik.debugPlaceStone(Feld.Inhalt.leer,
					position.getXAxis(), position.getYAxis()));
			contextMenu.add(setzeLeer);
			JMenuItem setzeWeiss = new JMenuItem("weißen Stein setzen");
			setzeWeiss.addActionListener(actionEvent -> muehleLogik.debugPlaceStone(Feld.Inhalt.weiss,
					position.getXAxis(), position.getYAxis()));
			contextMenu.add(setzeWeiss);
			JMenuItem setzeSchwarz = new JMenuItem("schwarzen Stein setzen");
			setzeSchwarz.addActionListener(actionEvent -> muehleLogik.debugPlaceStone(Feld.Inhalt.schwarz,
					position.getXAxis(), position.getYAxis()));
			contextMenu.add(setzeSchwarz);
			contextMenu.show(button, clickPosX, clickPosY);
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

		Object source = mouseEvent.getSource();
		if (!(source instanceof CircleButton)) {
			return;
		}

		CircleButton button = (CircleButton) source;

		if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
			Position position = getPosition4Button(button);
			if (position == null) {
				return;
			}
			muehleLogik.handlePlayAction(position.getXAxis(), position.getYAxis());

		} else if (SwingUtilities.isRightMouseButton(mouseEvent) && showStoneMenu) {
			showContextMenu4Button(mouseEvent.getX(), mouseEvent.getY(), button);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
