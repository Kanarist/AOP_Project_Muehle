import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
// ToDo: add lines under/right of smallest square
// ToDo: add circle on square. The position must depend on square

public class DrawBoard extends JPanel{

	private final int squareSize = 470;
	private static int fieldId = 0;
	private ArrayList<PointField> fields = new ArrayList<>(24);
	private int xAxis = 120;										//	needs to rework!
	private int yAxis = 110;										//	needs to rework!
	private Position fieldPosition = new Position(xAxis, yAxis);	//	needs to rework! 
//	private Rect rect = new Rect(fieldPosition, rectWidthAndHeight);
	
	public DrawBoard() {
		setPreferredSize(new Dimension(600, 600));
		for(int i = 0; i < 24; i++) {
			fields.add(new PointField(fieldId, fieldPosition));
			fieldId++;
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D figureRect = (Graphics2D) g;
		Graphics2D fieldPoint = (Graphics2D) g;
		Graphics2D line = (Graphics2D) g;
		
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int newSquareSize;
		
		for(int i = 0; i < 3; i++) {
			figureRect.setStroke(new BasicStroke(3));
			newSquareSize = squareSize - i * 150;
			int x = (panelWidth - newSquareSize)/2;
			int y = (panelHeight - newSquareSize)/2;
			figureRect.setColor(Color.BLACK);
			figureRect.drawRect(x,y,newSquareSize,newSquareSize);
			
			if(i < 1) {
				
				// senkrechte Linien
				line.drawLine(panelWidth/2, y, panelWidth/2, (panelHeight-squareSize + 2 * 150)/2); 	// It Work
				line.drawLine(panelWidth/2, (panelHeight + newSquareSize)/2, panelWidth/2, ((panelHeight + panelHeight-squareSize + 2 * 150)/2)-(panelHeight-squareSize + 2 * 150)/2+85);
				
				// waagerechte Linien
				line.drawLine(x, panelHeight/2, (panelWidth-squareSize + 2 * 150)/2, panelHeight/2);	// It Work
				line.drawLine((panelWidth + newSquareSize)/2, panelHeight/2, (panelWidth+panelWidth-squareSize + 2 * 150)/2-(panelWidth-squareSize + 2 * 150)/2+85, panelHeight/2);

			}
			
			
			for(int j = 0; j < fields.size(); j++) {
				PointField field = fields.get(i);
				newSquareSize = squareSize - i * 150;
				
				fieldPoint.setStroke(new BasicStroke(8));
				
				fieldPoint.setColor(Color.BLACK);
				fieldPoint.drawOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
				fieldPoint.setColor(Color.WHITE);
				fieldPoint.fillOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
				fieldPoint.setColor(Color.BLACK);
				fieldPoint.drawString(String.valueOf(field.getId()), field.getPosition().getX_Axis()+field.getDiameter()/2-5,field.getPosition().getY_Axis()+field.getDiameter()/2-5);
			}
		}
		
		
		
//		for(PointField field : fields) {
//			
//			fieldPoint.setStroke(new BasicStroke(8));
//			
//			fieldPoint.setColor(Color.BLACK);
//			fieldPoint.drawOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
//			fieldPoint.setColor(Color.WHITE);
//			fieldPoint.fillOval(field.getPosition().getX_Axis(), field.getPosition().getY_Axis(), field.getDiameter(), field.getDiameter());
//			fieldPoint.setColor(Color.BLACK);
//			fieldPoint.drawString(String.valueOf(field.getId()), field.getPosition().getX_Axis()+field.getDiameter()/2-5,field.getPosition().getY_Axis()+field.getDiameter()/2-5);
//				
//		}
		
		
	}

	
    
	
	
}
