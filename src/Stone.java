import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Stone extends JPanel {

	private int ovalWidth = 200;
	private int ovalHeight = 100;
    private boolean isWhiteStone;
    private final int stoneRadius = 35;


    public Stone(boolean isWhiteStone) {
        this.isWhiteStone = isWhiteStone;
        setOpaque(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isWhiteStone) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(Color.BLACK);
        }

        g2d.fillOval(0, 0, stoneRadius * 2, stoneRadius * 2);

    }

    public int getRadius() {
    	return stoneRadius;
    }
}