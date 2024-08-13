
public class Rect{

	private Position position;
	private int size = 500;
	
	public Rect(Position position, int size) {
		this.size = size;
		this.position = position;
	}
	

	public int getRectWidthAndHeight() {
		return size;
	}

	public void setRectWidthAndHeight(int size) {
		this.size = size;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
}
