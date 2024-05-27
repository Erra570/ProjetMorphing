package triangle;

public class CercleCirconscrit {
	private Point centre;
	private double rayon;
	
	
	public CercleCirconscrit(Point centre, double rayon) {
		this.centre=centre;
		this.rayon=rayon;
	}
	
	public boolean contient(Point point) {
		double distanceX = point.getX() - centre.getX();
		double distanceY = point.getY() - centre.getY();
		double distancePointCentre = distanceX * distanceX + distanceY * distanceY;
		return distancePointCentre <= rayon*rayon;
	}
}
