package muehle.GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Stone extends JPanel {


	private static final long serialVersionUID = -173561275626778530L;
	private final int stoneRadius = 35;
	private boolean isWhite;
	
	public Stone(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public int getStoneRadius() {
		return stoneRadius;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isWhite) {
            g2d.setColor(Color.WHITE); 
        } else {
            g2d.setColor(Color.BLACK);
        }

        g2d.fillOval(0, 0, stoneRadius * 2, stoneRadius * 2);

    }
	
	
}