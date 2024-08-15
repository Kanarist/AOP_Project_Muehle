import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
// ToDo: fix circle on last square

public class DrawBoard extends JPanel{

	private final int squareSize = 650;
	private ArrayList<PointField> fields = new ArrayList<>(24);
	private int xAxis = 120;										//	needs to rework!
	private int yAxis = 110;										//	needs to rework!
	private Position fieldPosition = new Position(xAxis, yAxis);	//	needs to rework! 
//	private Rect rect = new Rect(fieldPosition, rectWidthAndHeight);
	
	public DrawBoard() {
		setPreferredSize(new Dimension(600, 600));
		for(int i = 0; i < 24; i++) {
			fields.add(new PointField(fieldPosition));
			
		}
	}
	
	public void circleCreator(int squareSize, int panelWidth, int panelHeight) {
	    fields.clear();

	    int x = (panelWidth - squareSize) / 2;
	    int y = (panelHeight - squareSize) / 2;

	    // corner circles
	    fields.add(new PointField(new Position(x, y)));  				
	    fields.add(new PointField(new Position(x + squareSize, y)));  
	    fields.add(new PointField(new Position(x, y + squareSize)));  
	    fields.add(new PointField(new Position(x + squareSize, y + squareSize)));  

	    // middle circles
	    fields.add(new PointField(new Position(x + squareSize / 2, y)));  
	    fields.add(new PointField(new Position(x + squareSize / 2, y + squareSize)));  
	    fields.add(new PointField(new Position(x, y + squareSize / 2)));  
	    fields.add(new PointField(new Position(x + squareSize, y + squareSize / 2)));  
	}

	public void paintComponent(Graphics g) {
		Graphics2D fieldPoint = (Graphics2D) g;
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
			for(int j = 0; j < fields.size(); j++) {
				PointField field = fields.get(j);
				circleCreator(newSquareSize, panelWidth-field.getDiameter(), panelHeight-field.getDiameter());
				
				fieldPoint.setStroke(new BasicStroke(8));
				fieldPoint.setColor(Color.BLACK);
				fieldPoint.drawOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
				fieldPoint.setColor(Color.WHITE);
				fieldPoint.fillOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
			
				
			}
			
		}
		
	}
	
}
