import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

// ToDo: fix design of stones
// ToDO: fix of removeAll() function
// ToDo: connect of logic

public class DrawBoard extends JPanel implements MouseListener{

	private final int squareSize = 650;
	private boolean isHovered;
	
	public DrawBoard() {
		setPreferredSize(new Dimension(600, 600));
		
		
	}
	
	
	public void paintComponent(Graphics g) {
		removeAll(); 
		super.paintComponent(g); 
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
			
			//  create of circles
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

	        for (int[] circle : circles) {
//	        	...
	        	CircleButton button = new CircleButton();
	            button.setEnabled(false);
	            button.setBounds(circle[0], circle[1], button.getCircleDiameter(), button.getCircleDiameter());
	        	button.setBorder(new RoundedBorder(Color.black, 80));
	        	button.setBackground(Color.WHITE);
	            button.addMouseListener(this); 
	        	add(button);
	        }
	        
		}

		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() instanceof CircleButton) {

			CircleButton button = (CircleButton) e.getSource();
	        Stone stone = new Stone(false);
	        button.add(stone);
	        stone.setBounds(-15, -15, stone.getRadius() * 2, stone.getRadius() * 2);
	        System.out.println("Button pressed!");

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
		if (e.getSource() instanceof JButton) {
			CircleButton button = (CircleButton) e.getSource();
	        System.out.println("Cursor is on Button!");
	        isHovered = true;
	        button.setBackground(button.getBackground().brighter());
	    }
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
			CircleButton button = (CircleButton) e.getSource();
	        System.out.println("Cursor NOT is on Button!");
	        isHovered = false;
	        button.setBackground(button.getBackground().darker());
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
