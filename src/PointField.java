
public class PointField {

	private final int diameter = 40;
	private Position position;
	
	
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
	
	
	
}
