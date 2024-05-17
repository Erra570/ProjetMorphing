package morphingFonction;

/**
 * Classe Point représentant un point en deux dimensions avec des coordonnées x et y.
 * Contient des méthodes pour manipuler et comparer des points.
 */
public class Point {
	private int x;
	private int y;
	
	/**
     * Getter de la coordonnée x du point.
     * 
     * @return coordonnée x du point
     */
	public int getX() {
		return x;
	}
	
	/**
     * Setter de la coordonnée x du point.
     * 
     * @param x la nouvelle coordonnée x du point
     */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
     * Getter de la coordonnée y du point.
     * 
     * @return coordonnée y du point
     */
	public int getY() {
		return y;
	}
	
	/**
     * Setter de la coordonnée y du point.
     * 
     * @param x la nouvelle coordonnée y du point
     */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
     * Constructeur de la classe Point.
     * 
     * @param x la coordonnée x du point
     * @param y la coordonnée y du point
     */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
    /**
     * Calcule la position relative de ce point par rapport à une droite définie par deux points.
     * 
     * @param a le premier point définissant la droite
     * @param b le deuxième point définissant la droite
     * @return la position du point par rapport à la droite
     */
	public double position(Point a, Point b) {
		return((((double) (a.getX() - this.getX())) * ((double) (b.getY() - this.getY()))) - (((double) (a.getY() - this.getY())) * ((double) (b.getX() - this.getX()))));
	}
	
	/**
     * Détermine si ce point est à l'intérieur d'un triangle défini par trois autres points.
     * 
     * @param a le premier sommet du triangle
     * @param b le deuxième sommet du triangle
     * @param c le troisième sommet du triangle
     * @return true si ce point est à l'intérieur du triangle, false sinon
     */
	public boolean dansTriangle(Point a, Point b, Point c) {
		return (((position(c,a) >= 0) && (position(a,b) >= 0) && (position(b,c) >= 0))
				|| ((position(c,a) <= 0) && (position(a,b) <= 0) && (position(b,c) <= 0)));
	}
}
