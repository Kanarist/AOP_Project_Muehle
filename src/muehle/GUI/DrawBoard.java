package muehle.GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.Border;

import model.BesetztesFeldExeption;
import model.Feld;
import model.MuehleMain;
import model.Spieler;

// ToDo: fix design of stones
// ToDo: connect of logic

public class DrawBoard extends JPanel implements MouseListener{

	private final MuehleMain muehleMain;
	private final int squareSize = 650;
	private boolean turn;
	private int maxCountOfStones = 0;
	private CircleButton[][]buttons = new CircleButton[8][8];

	private final Map<CircleButton, Position> button2Position = new HashMap<>();
	private final Map<Position, CircleButton> position2button = new HashMap<>();
	
	public DrawBoard(MuehleMain muehleMain) {
		this.muehleMain = muehleMain;
		setPreferredSize(new Dimension(600, 600));
	}
	
	// create squares and lines of board
	public void createSquaresAndLines(Graphics g) {
		Graphics2D lineAndSquare = (Graphics2D) g;
		
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int newSquareSize;
		
		for(int i = 0; i < 3; i++) {
			lineAndSquare.setStroke(new BasicStroke(5));
			newSquareSize = squareSize - i * 200;
			int x = (panelWidth - newSquareSize)/2;
			int y = (panelHeight - newSquareSize)/2;
			lineAndSquare.setColor(Color.BLACK);
			lineAndSquare.drawRect(x,y,newSquareSize,newSquareSize);
			
			// draw lines
			if(i == 0) {
				
				// vertical lines
				lineAndSquare.drawLine(panelWidth/2, y, panelWidth/2, (panelHeight-squareSize + 2 * 200)/2); 	// top
				lineAndSquare.drawLine(panelWidth/2, (panelHeight + newSquareSize)/2, panelWidth/2, ((panelHeight + panelHeight-squareSize + 2 * 200)/2)-(panelHeight-squareSize + 2 * 200)/2+125);	// bottom
				
				// horizontal lines
				lineAndSquare.drawLine(x, panelHeight/2, (panelWidth-squareSize + 2 * 200)/2, panelHeight/2);	// left
				lineAndSquare.drawLine((panelWidth + newSquareSize)/2, panelHeight/2, (panelWidth+panelWidth-squareSize + 2 * 200)/2-(panelWidth-squareSize + 2 * 200)/2+125, panelHeight/2);		// reight

			}
		}
	}
	
	//  create of circles
	public void createFields(Graphics g) {
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int newSquareSize;
		
		for(int i = 0; i < 3; i++) {
			
			newSquareSize = squareSize - i * 200;
			int x = (panelWidth - newSquareSize)/2;
			int y = (panelHeight - newSquareSize)/2;
			
	        int[][] circles = {
	            {x - 30 / 2, y - 30 / 2}, 										// top left
	            {x + newSquareSize - 30 / 2, y - 30 / 2}, 						// top right
	            {x - 30 / 2, y + newSquareSize - 30 / 2}, 						// bottom left
	            {x + newSquareSize - 30 / 2, y + newSquareSize - 30 / 2}, 		// bottom right
	            {x + newSquareSize / 2 - 30 / 2, y - 30 / 2}, 					// top middle
	            {x + newSquareSize / 2 - 30 / 2, y + newSquareSize - 30 / 2}, 	// bottom middle
	            {x - 30 / 2, y + newSquareSize / 2 - 30 / 2}, 					// left middle
	            {x + newSquareSize - 30 / 2, y + newSquareSize / 2 - 30 / 2}  	// right middle
	            
	        };

	        for (int j = 0; j < circles.length; j++) {
	        	int[] circle = circles[j];
	        	CircleButton button = new CircleButton();
	            button.setEnabled(false);
	            button.setBounds(circle[0], circle[1], button.getCircleDiameter(), button.getCircleDiameter());
	        	button.setBorder(new RoundedBorder(Color.BLACK, 80));
	        	button.setBackground(Color.WHITE);
	            button.addMouseListener(this); 
	        	add(button);
	        	buttons[i][j] = button;
	        }
	        
		}
		
		button2Position.put(buttons[0][0], new Position(0, 0));
		button2Position.put(buttons[0][1], new Position(1, 1));
		button2Position.put(buttons[0][2], new Position(2, 2));
		button2Position.put(buttons[1][0], new Position(7, 0));
		button2Position.put(buttons[1][1], new Position(6, 1));
		button2Position.put(buttons[1][2], new Position(5, 2));
		button2Position.put(buttons[2][0], new Position(0, 7));
		button2Position.put(buttons[2][1], new Position(1, 6));
		button2Position.put(buttons[2][2], new Position(2, 5));
		button2Position.put(buttons[3][0], new Position(7, 7));
		button2Position.put(buttons[3][1], new Position(6, 6));
		button2Position.put(buttons[3][2], new Position(5, 5));
		button2Position.put(buttons[4][0], new Position(3, 0));
		button2Position.put(buttons[4][1], new Position(3, 1));
		button2Position.put(buttons[4][2], new Position(3, 2));
		button2Position.put(buttons[5][0], new Position(4, 7));
		button2Position.put(buttons[5][1], new Position(4, 6));
		button2Position.put(buttons[5][2], new Position(4, 5));
		button2Position.put(buttons[6][0], new Position(0, 3));
		button2Position.put(buttons[6][1], new Position(1, 3));
		button2Position.put(buttons[6][2], new Position(2, 3));
		button2Position.put(buttons[7][0], new Position(7, 4));
		button2Position.put(buttons[7][1], new Position(6, 4));
		button2Position.put(buttons[7][2], new Position(5, 4));
		
		for(Entry<CircleButton, Position> entry : button2Position.entrySet()) {
			position2button.put(entry.getValue(), entry.getKey());
		}
		
	}
	
	public void paintComponent(Graphics g) {
		removeAll(); 
		super.paintComponent(g); 
		createSquaresAndLines(g);
		createFields(g);
	}
	
	public void updateBoard() {
		Feld[][] felder = muehleMain.getSpielbrett().getFelder();
		
		for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                CircleButton button = position2button.get(new Position(i,j));
                if(button == null) {
                	continue;
                }
                Feld.Inhalt inhalt = felder[i][j].getInhalt();
                if (inhalt == Feld.Inhalt.weiss) {
                    button.setBackground(Color.WHITE);
                } else if (inhalt == Feld.Inhalt.schwarz) {
                    button.setBackground(Color.BLACK);
                } else if (inhalt == Feld.Inhalt.verboten) {
                    button.setEnabled(false);
                } else {
                    button.setBackground(Color.GRAY); 
                }
            }
        }
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(maxCountOfStones < 18) {
			if (e.getSource() instanceof CircleButton) {
				CircleButton button = (CircleButton) e.getSource();
		        Stone stone = new Stone(turn);
		        turn = turn ? false : true;
		        button.add(stone);
		        stone.setBounds(-15, -15, stone.getStoneRadius() * 2, stone.getStoneRadius() * 2);
		        System.out.println("Button pressed!"); // TEST
		        
		        // Finden Sie die Position des gedr�ckten Buttons
		        final Position position = button2Position.get(button);
		        
	            if(position != null) {
	            	// Anzahl der Steine muss ge�ndert werden, da jeder Spieler je 9 Steine haben
	            	try {
	            		Spieler currentPlayer = turn ? new Spieler("Spieler Wei�", Spieler.Farbe.WEISS, 9): new Spieler("Spieler Schwarz", Spieler.Farbe.SCHWARZ, 9);
	            		
	            		muehleMain.getSpielbrett().setzeStein(currentPlayer, position.getXAxis(), position.getYAxis());
	            		
	            		updateBoard();
	    		        
	    		        if(muehleMain.getSpielbrett().pruefeMuele(position.getXAxis(), position.getYAxis())) {
	    		        	System.out.println("M�hle gemacht");
	    		        }
	    		        
	    		        
	    		        turn = !turn;
	    		        maxCountOfStones++;

	            	}catch(BesetztesFeldExeption e1) {
	                    System.out.println("Feld ist bereits besetzt.");

	            	}
	            	
	            }
		        
		    }
		}else if (maxCountOfStones > 18){
			
		}
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
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }


	    public boolean isBorderOpaque() {
	        return true;
	    }


	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.setColor(color);
	    	g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    }
	}
	
	
}
