package triangle;

public class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p2 = (Point) obj;
			if (this.getX() == p2.getX() && this.getY() == p2.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
