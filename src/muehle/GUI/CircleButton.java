package muehle.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JButton;

//Spielstein als Button
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

        //whaerend draufdruecken wird Button dunkler
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground().brighter());
        }
        
        Dimension size = getSize();
        int width = size.width - 1;
        int height = size.height - 1;

        g.fillOval(0, 0, width, height);
        
        g.setColor(Color.RED);
        drawCenteredText(g, getText(), width, height);

    }
    
    //schreibt Text zentral auf Button
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
