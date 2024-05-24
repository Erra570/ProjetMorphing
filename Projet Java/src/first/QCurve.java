package first;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Classe représentant une courbe quadratique avec des points de contrôle.
 * Cette classe permet de créer, dessiner, mettre à jour et supprimer des courbes quadratiques.
 * 
 * @author Nicolas F
 */
public class QCurve {

    private double XDepart;
    private double YDepart;
    private double XControl;
    private double YControl;
    private double XFin;
    private double YFin;
    private List<Circle> pointsCourbe;
    private Color couleur;

    /**
     * Constructeur de la classe QCurve.
     * 
     * @param xd      Coordonnée x du point de départ.
     * @param yd      Coordonnée y du point de départ.
     * @param xc      Coordonnée x du point de contrôle.
     * @param yc      Coordonnée y du point de contrôle.
     * @param xf      Coordonnée x du point de fin.
     * @param yf      Coordonnée y du point de fin.
     * @param couleur Couleur de la courbe.
     */
    public QCurve(double xd, double yd, double xc, double yc, double xf, double yf, Color couleur) {
        this.XDepart = xd;
        this.YDepart = yd;
        this.XControl = xc;
        this.YControl = yc;
        this.XFin = xf;
        this.YFin = yf;
        this.pointsCourbe = new ArrayList<Circle>();
        this.couleur = couleur;
    }

    /**
     * Méthode pour obtenir un point de la courbe à un paramètre t donné.
     * 
     * @param t Le paramètre t (0 <= t <= 1).
     * @return Un cercle représentant le point de la courbe.
     * @throws IllegalArgumentException Si t est hors de la plage 0 <= t <= 1.
     */
    public Circle getPoint(double t) throws IllegalArgumentException {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t out of range 0 <= t <= 1");
        }
        double x = Math.pow(1 - t, 2) * this.XDepart + 2 * (1 - t) * t * this.XControl + Math.pow(t, 2) * this.XFin;
        double y = Math.pow(1 - t, 2) * this.YDepart + 2 * (1 - t) * t * this.YControl + Math.pow(t, 2) * this.YFin;
        return new Circle(x, y, 5);
    }

    /**
     * Méthode pour générer les points de la courbe quadratique.
     * 
     * @param step Le nombre de segments pour dessiner la courbe.
     * @return La liste des cercles représentant les points de la courbe.
     */
    public List<Circle> courbeQuad(int step) {
        pointsCourbe.clear(); // Effacer les points précédents
        for (int i = 0; i <= step; i++) {
            double t = (double) i / step;
            getPoint(t).getStyleClass().add("pts");
            pointsCourbe.add(getPoint(t));
        }
        return pointsCourbe;
    }

    public double getXDepart() {
        return XDepart;
    }

    public void setXDepart(double xDepart) {
        XDepart = xDepart;
    }

    public double getYDepart() {
        return YDepart;
    }

    public void setYDepart(double yDepart) {
        YDepart = yDepart;
    }

    public double getXControl() {
        return XControl;
    }

    public void setXControl(double xControl) {
        XControl = xControl;
    }

    public double getYControl() {
        return YControl;
    }

    public void setYControl(double yControl) {
        YControl = yControl;
    }

    public double getXFin() {
        return XFin;
    }

    public void setXFin(double xFin) {
        XFin = xFin;
    }

    public double getYFin() {
        return YFin;
    }

    public void setYFin(double yFin) {
        YFin = yFin;
    }

    public void setColor(Color coul) {
        this.couleur = coul;
    }

    /**
     * Met à jour la courbe en fonction des nouveaux points de départ, de contrôle et de fin.
     * 
     * @param depart   Cercle représentant le point de départ.
     * @param controle Cercle représentant le point de contrôle.
     * @param fin      Cercle représentant le point de fin.
     * @param root     Pane où la courbe est dessinée.
     */
    public void update(Circle depart, Circle controle, Circle fin, Pane root) {
        this.setXControl(controle.getCenterX());
        this.setYControl(controle.getCenterY());
        this.setXDepart(depart.getCenterX());
        this.setYDepart(depart.getCenterY());
        this.setXFin(fin.getCenterX());
        this.setYFin(fin.getCenterY());
        this.drawCurve(root);
    }

    /**
     * Dessine la courbe sur le panneau.
     * 
     * @param root Pane où la courbe est dessinée.
     */
    public void drawCurve(Pane root) {
        root.getChildren().removeAll(pointsCourbe);
        pointsCourbe = this.courbeQuad(300); // Nombre de points pour dessiner la courbe
        for (Circle p : pointsCourbe) {
            p.setRadius(1); // Utiliser un rayon plus petit pour les points de la courbe
            p.setFill(this.couleur); // Changer la couleur pour différencier les points de la courbe
            root.getChildren().add(p);
        }
    }

    /**
     * Supprime la courbe du panneau.
     * 
     * @param root Pane où la courbe est dessinée.
     */
    public void delete(Pane root) {
        root.getChildren().removeAll(pointsCourbe);
    }

    /**
     * Change la couleur de la courbe et redessine la courbe avec la nouvelle couleur.
     * 
     * @param coul Nouvelle couleur de la courbe.
     * @param root Pane où la courbe est dessinée.
     */
    public void colorChange(Color coul, Pane root) {
        root.getChildren().removeAll(pointsCourbe);
        pointsCourbe = this.courbeQuad(500); // Nombre de points pour dessiner la courbe
        for (Circle p : pointsCourbe) {
            p.setRadius(1); // Utiliser un rayon plus petit pour les points de la courbe
            p.setFill(coul); // Changer la couleur pour différencier les points de la courbe
            root.getChildren().add(p);
        }
        this.setColor(coul);
    }
}