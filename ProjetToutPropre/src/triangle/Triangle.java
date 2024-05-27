package triangle;

public class Triangle {
	private Cote cote_1;
	private Cote cote_2;
	private Cote cote_3;
	private Point point_1;
	private Point point_2;
	private Point point_3;
	private CercleCirconscrit cercle;
	
	public Triangle(Cote c1, Cote c2, Cote c3) {
		this.cote_1=c1;
		this.cote_2=c2;
		this.cote_3=c3;
		this.point_1=c1.getDebut();
		this.point_2=c2.getDebut();
		this.point_3=c3.getDebut();
	}
	
	private CercleCirconscrit calculCercleCirconscrit() {
		double a = point_1.getX()*point_1.getX() + point_1.getY()*point_1.getY();
	}
}
