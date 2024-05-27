package triangle;

public class Triangle {
	private Point point_1;
	private Point point_2;
	private Point point_3;
	private CercleCirconscrit cercle;

	public Triangle(Point p1, Point p2, Point p3) throws Exception {
		this.point_1 = p1;
		this.point_2 = p2;
		this.point_3 = p3;
		this.cercle = calculCercleCirconscrit();
	}

	private CercleCirconscrit calculCercleCirconscrit() throws Exception {
		double a = point_1.getX() * point_1.getX() + point_1.getY() * point_1.getY();
		double b = point_2.getX() * point_2.getX() + point_2.getY() * point_2.getY();
		double c = point_3.getX() * point_3.getX() + point_3.getY() * point_3.getY();
		double delta = point_1.getX() * (point_2.getY() - point_3.getY())
				- point_1.getY() * (point_2.getX() - point_3.getX())
				+ (point_2.getX() * point_3.getY() - point_2.getY() * point_3.getX());
		if (delta == 0) {
			throw new Exception("Les points sont collinéaires");
		}
		double detCentreX = a * (point_2.getY() - point_3.getY()) - point_1.getY() * (b - c)
				+ (b * point_3.getY() - point_2.getY() * c);
		double detCentreY = a * (point_2.getX() - point_3.getX()) - point_1.getX() * (b - c)
				+ (b * point_3.getX() - point_2.getX() * c);
		double xCentre = detCentreX / (2 * delta);
		double yCentre = -detCentreY / (2 * delta);
		

		Point centre = new Point(xCentre, yCentre);
		
		double rayon = Math.abs(point_1.distance(point_2)*point_2.distance(point_3)*point_3.distance(point_1)/(2*delta));

		return new CercleCirconscrit(centre, rayon);
	}

	public CercleCirconscrit getCercle() {
		return this.cercle;
	}

	public Point getPoint_1() {
		return point_1;
	}

	public Point getPoint_2() {
		return point_2;
	}

	public Point getPoint_3() {
		return point_3;
	}

}