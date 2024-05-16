package morphingFonction;

public class Point {
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double position(Point a, Point b) {
		return((((double) (a.getX() - this.getX())) * ((double) (b.getY() - this.getY()))) - (((double) (a.getY() - this.getY())) * ((double) (b.getX() - this.getX()))));
	}
	
	
	public boolean dansTriangle(Point a, Point b, Point c) {
		return (((position(c,a) >= 0) && (position(a,b) >= 0) && (position(b,c) >= 0))
				|| ((position(c,a) <= 0) && (position(a,b) <= 0) && (position(b,c) <= 0)));
	}
}
