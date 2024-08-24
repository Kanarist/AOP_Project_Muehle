import java.awt.*; 
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.BesetztesFeldExeption;
import model.Feld;
import model.Spielbrett;
import model.Spieler;

// ToDo: fix design of stones
// ToDo: connect of logic

public class DrawBoard extends JPanel implements MouseListener{

	private Spielbrett spielbrett;
	private final int squareSize = 650;
	private boolean turn;
	private int maxCountOfStones = 0;
	private CircleButton[][]buttons = new CircleButton[8][8];
	
	public DrawBoard(Spielbrett spielbrett) {
		this.spielbrett = spielbrett;
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
	}
	
	public void paintComponent(Graphics g) {
		removeAll(); 
		super.paintComponent(g); 
		createSquaresAndLines(g);
		createFields(g);
	}
	
	public void updateBoard() {
		Feld[][] felder = spielbrett.getFelder();
		
		for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                CircleButton button = buttons[i][j];
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
		        
		        // Finden Sie die Position des gedrückten Buttons
	            int row = -1;
	            int column = -1;
	            for (int i = 0; i < buttons.length; i++) {
	                for (int j = 0; j < buttons[i].length; j++) {
	                    if (buttons[i][j] == button) {
	                        row = i;
	                        column = j;
	                        break;
	                    }
	                }
	                if (row != -1) break;
	            }
	            
	            if(row != -1 && column != -1) {
	            	// Anzahl der Steine muss geändert werden, da jeder Spieler je 9 Steine haben
	            	try {
	            		Spieler currentPlayer = turn ? new Spieler("Spieler Weiß", Spieler.Farbe.WEISS, 9): new Spieler("Spieler Schwarz", Spieler.Farbe.SCHWARZ, 9);
	            		spielbrett.setzeStein(currentPlayer, column, row);
	            		
	            		updateBoard();
	    		        
	    		        if(spielbrett.pruefeMuele(column, row)) {
	    		        	System.out.println("Mühle gemacht");
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
