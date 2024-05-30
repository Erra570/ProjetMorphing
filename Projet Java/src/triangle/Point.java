package triangle;

import morphingFonction.PointMorphing;

/**
 * Représente un point dans un plan cartésien.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructeur d'un point avec ses coordonnées x et y.
     * @param x La coordonnée x du point.
     * @param y La coordonnée y du point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtient la coordonnée x du point.
     * @return La coordonnée x du point.
     */
    public double getX() {
        return x;
    }

    /**
     * Définit la coordonnée x du point.
     * @param x La nouvelle coordonnée x du point.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Obtient la coordonnée y du point.
     * @return La coordonnée y du point.
     */
    public double getY() {
        return y;
    }

    /**
     * Définit la coordonnée y du point.
     * @param y La nouvelle coordonnée y du point.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Détermine si un objet est égal à ce point.
     * @param obj L'objet à comparer.
     * @return true si l'objet est un point avec les mêmes coordonnées, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p2 = (Point) obj;
            return this.getX() == p2.getX() && this.getY() == p2.getY();
        }
        return false;
    }
    
    /**
     * Calcule la distance euclidienne entre ce point et un autre point.
     * @param point Le point jusqu'auquel calculer la distance.
     * @return La distance euclidienne entre ce point et le point spécifié.
     */
    public double distance(Point point) {
        double dx = this.getX() - point.getX();
        double dy = this.getY() - point.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    /**
     * Calcule le carré de la distance euclidienne entre ce point et un autre point.
     * @param point Le point jusqu'auquel calculer la distance.
     * @return Le carré de la distance euclidienne entre ce point et le point spécifié.
     */
    public double distance2(Point point) {
        double dx = this.getX() - point.getX();
        double dy = this.getY() - point.getY();
        return dx*dx + dy*dy;
    }

    /**
     * Obtient une représentation sous forme de chaîne de ce point.
     * @return Une chaîne représentant les coordonnées x et y de ce point.
     */
    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }
    
    /**
     * Convertit ce point en un point morphing avec des coordonnées entières.
     * @return Un point morphing représentant ce point.
     */
    public PointMorphing transtypage() {
        return new PointMorphing((int) this.x, (int) this.y);
    }
}
