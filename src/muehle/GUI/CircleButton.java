package muehle.GUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JButton;

public class CircleButton extends JButton{
	
	private static final long serialVersionUID = 2303935598718482558L;
	
	private final int circleDiameter = 50;
	
	
	public CircleButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
    }
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground().brighter());
        }
        
        int width = getSize().width - 1;
        int height = getSize().height - 1;

        g.fillOval(0, 0, width, height);
        
        g.setColor(Color.RED);
        drawCenteredText(g, getText(), width, height);

    }
    
    private void drawCenteredText(Graphics g, String text, int width, int height) {
    	if(!text.isBlank()) {
            FontMetrics fm = g.getFontMetrics();
            int x = (width - fm.stringWidth(text)) / 2;
            int y = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);
            g.drawString(text, x, y);
    	}
    }
    
	public int getCircleDiameter() {
		return circleDiameter;
	}

	
}
