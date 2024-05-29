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
	
	public double distance(Point point) {
		double dx = this.getX() - point.getX();
		double dy = this.getY() - point.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public double distance2(Point point) {
		double dx = this.getX() - point.getX();
		double dy = this.getY() - point.getY();
		return dx*dx + dy*dy;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	

}
