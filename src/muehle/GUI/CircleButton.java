package muehle.GUI;
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

        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

    }
    
	public int getCircleDiameter() {
		return circleDiameter;
	}

	
}
