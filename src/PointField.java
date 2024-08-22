import javax.swing.JButton;

public class PointField extends JButton{

	private final int diameter = 40;
	private Position position;
	private boolean isActive;
	
	public PointField(Position position) {
		this.position = position;
	}

	public int getDiameter() {
		return diameter;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
