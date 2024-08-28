package muehle.GUI;

public class Position {
	private int xAxis;
	private int yAxis;
	
	public Position(int x, int y) {
		this.xAxis = x;
		this.yAxis = y;
	}
	
	public int getXAxis() {
		return xAxis;
	}
	public void setXAxis(int x_Axis) {
		this.xAxis = x_Axis;
	}
	
	public int getYAxis() {
		return yAxis;
	}
	public void setYAxis(int y_Axis) {
		this.yAxis = y_Axis;
	}
	
	@Override
    public boolean equals(Object obj) {
		return obj instanceof Position && xAxis == ((Position)obj).xAxis && yAxis == ((Position)obj).yAxis;
    }
	
	@Override
	public int hashCode() {
		return xAxis;
	}
	
}
