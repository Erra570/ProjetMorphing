package triangle;

/**
 * La classe CercleCirconscrit représente un cercle circonscrit autour d'un triangle.
 */
public class CercleCirconscrit {
	
	private Point centre; // Centre du cercle
	private double rayon; // Rayon du cercle
	
	/**
	 * Constructeur de la classe CercleCirconscrit.
	 * @param centre Le centre du cercle.
	 * @param rayon Le rayon du cercle.
	 */
	public CercleCirconscrit(Point centre, double rayon) {
		this.centre = centre;
		this.rayon = rayon;
	}
	
	/**
	 * Vérifie si un point est situé à l'intérieur ou sur le bord du cercle.
	 * @param point Le point à tester.
	 * @return true si le point est contenu dans le cercle, sinon false.
	 */
	public boolean contient(Point point) {
		double distanceX = point.getX() - centre.getX();
		double distanceY = point.getY() - centre.getY();
		double distancePointCentre = distanceX * distanceX + distanceY * distanceY;
		return distancePointCentre <= rayon * rayon;
	}

	/**
	 * Obtient le centre du cercle.
	 * @return Le centre du cercle.
	 */
	public Point getCentre() {
		return centre;
	}

	/**
	 * Définit le centre du cercle.
	 * @param centre Le nouveau centre du cercle.
	 */
	public void setCentre(Point centre) {
		this.centre = centre;
	}

	/**
	 * Obtient le rayon du cercle.
	 * @return Le rayon du cercle.
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * Définit le rayon du cercle.
	 * @param rayon Le nouveau rayon du cercle.
	 */
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}
}
