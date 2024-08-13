
public class PointField {

	private boolean isOccupied;
	private int id;
	private final int diameter = 40;
	private Position position;
	
	
	public PointField(int id, Position position) {
		this.id = id;
		this.position = position;
		this.isOccupied = false;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
