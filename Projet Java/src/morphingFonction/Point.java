package morphingFonction;

import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 * Classe représentant un point avec des coordonnées x et y.
 * Fournit des méthodes pour déterminer la position relative des points et vérifier l'appartenance à des figures géométriques.
 */
public class Point {
    private int x;  // Coordonnée x du point
    private int y;  // Coordonnée y du point

    /**
     * Getter pour la coordonnée x.
     * 
     * @return la coordonnée x
     */
    public int getX() {
        return x;
    }

    /**
     * Setter pour la coordonnée x.
     * 
     * @param x la nouvelle coordonnée x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter pour la coordonnée y.
     * 
     * @return la coordonnée y
     */
    public int getY() {
        return y;
    }

    /**
     * Setter pour la coordonnée y.
     * 
     * @param y la nouvelle coordonnée y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Constructeur de la classe Point.
     * 
     * @param x la coordonnée x
     * @param y la coordonnée y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calcule la position relative du point actuel par rapport aux points a et b.
     * Utilise le produit vectoriel pour déterminer la position.
     * 
     * @param a le point a
     * @param b le point b
     * @return la position relative
     */
    public double position(Point a, Point b) {
        return ((((double) (a.getX() - this.getX())) * ((double) (b.getY() - this.getY()))) 
                - (((double) (a.getY() - this.getY())) * ((double) (b.getX() - this.getX()))));
    }

    /**
     * Vérifie si le point actuel est à l'intérieur du triangle défini par les points a, b et c.
     * Utilise la méthode de position relative pour chaque côté du triangle.
     * 
     * @param a le point a
     * @param b le point b
     * @param c le point c
     * @return true si le point est à l'intérieur du triangle, false sinon
     */
    public boolean dansTriangle(Point a, Point b, Point c) {
        return (((position(c, a) >= 0) && (position(a, b) >= 0) && (position(b, c) >= 0)) 
                || ((position(c, a) <= 0) && (position(a, b) <= 0) && (position(b, c) <= 0)));
    }

    /**
     * Vérifie si le point actuel est à l'intérieur d'une figure définie par un tableau de points.
     * La figure est construite en utilisant des courbes quadratiques pour relier les points.
     * 
     * @param tab le tableau de points définissant la figure
     * @return true si le point est à l'intérieur de la figure, false sinon
     */
    public boolean dansFigure(Point[] tab) {
    	
    	// Création du contour de la figure
        Path path = new Path();
        path.getElements().add(new MoveTo(tab[0].getX(), tab[0].getY()));
        path.getElements().add(new QuadCurveTo(tab[2].getX(), tab[2].getY(), tab[1].getX(), tab[1].getY()));
        for (int i = 3; i < tab.length; i += 2) {
            path.getElements().add(new QuadCurveTo(tab[i+1].getX(), tab[i+1].getY(), tab[i].getX(), tab[i].getY()));
        }

        // Remplissage de la figure
        path.setFill(Color.TRANSPARENT);

        // Vérifie si la figure contient le point
        return path.contains(this.x, this.y);
    }
}
