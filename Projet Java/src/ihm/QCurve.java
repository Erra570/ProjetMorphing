package ihm;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * La classe QCurve représente une courbe quadratique de Bézier.
 * Elle fournit des méthodes pour générer des points de courbe, mettre à jour les points de contrôle de la courbe,
 * dessiner la courbe, supprimer la courbe et changer sa couleur.
 */
public class QCurve {

    // Définit les points de contrôle de la courbe et ses attributs
    private double XDepart; // Coordonnée X du point de départ
    private double YDepart; // Coordonnée Y du point de départ
    private double XControl; // Coordonnée X du point de contrôle
    private double YControl; // Coordonnée Y du point de contrôle
    private double XFin; // Coordonnée X du point de fin
    private double YFin; // Coordonnée Y du point de fin
    private List<Circle> curvePoints; // Liste des points de la courbe
    private Color couleur; // Couleur de la courbe

    /**
     * Constructeur pour initialiser la courbe quadratique de Bézier avec les points de contrôle et la couleur.
     *
     * @param xd      Coordonnée X du point de départ.
     * @param yd      Coordonnée Y du point de départ.
     * @param xc      Coordonnée X du point de contrôle.
     * @param yc      Coordonnée Y du point de contrôle.
     * @param xf      Coordonnée X du point de fin.
     * @param yf      Coordonnée Y du point de fin.
     * @param couleur Couleur de la courbe.
     */
    public QCurve(double xd, double yd, double xc, double yc, double xf, double yf, Color couleur) {
        // Initialise les attributs de la courbe
        this.XDepart = xd;
        this.YDepart = yd;
        this.XControl = xc;
        this.YControl = yc;
        this.XFin = xf;
        this.YFin = yf;
        this.curvePoints = new ArrayList<Circle>();
        this.couleur = couleur;
    }

    // Méthode pour générer un point sur la courbe pour un paramètre t donné
    public Circle getPoint(double t) throws IllegalArgumentException {
        // Calcule les coordonnées en utilisant la formule de Bézier quadratique
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t hors de la plage 0 <= t <= 1");
        }
        double x = Math.pow(1 - t, 2) * this.XDepart + 2 * (1 - t) * t * this.XControl + Math.pow(t, 2) * this.XFin;
        double y = Math.pow(1 - t, 2) * this.YDepart + 2 * (1 - t) * t * this.YControl + Math.pow(t, 2) * this.YFin;
        return new Circle(x, y, 5);
    }

    // Méthode pour générer une liste de points de la courbe
    public List<Circle> courbeQuad(int step) {
        curvePoints.clear(); // Effacer les points précédents
        for (int i = 0; i <= step; i++) {
            double t = (double) i / step;
            getPoint(t).getStyleClass().add("pts");
            curvePoints.add(getPoint(t));
        }
        return curvePoints;
    }

    // Getters et Setters pour les coordonnées des points de contrôle et la couleur
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

    // Méthode pour mettre à jour les points de contrôle de la courbe et dessiner la courbe
    public void update(Circle depart, Circle controle, Circle fin, Pane root) {
        this.setXControl(controle.getCenterX());
        this.setYControl(controle.getCenterY());
        this.setXDepart(depart.getCenterX());
        this.setYDepart(depart.getCenterY());
        this.setXFin(fin.getCenterX());
        this.setYFin(fin.getCenterY());
        this.drawCurve(root);
    }

    // Méthode pour dessiner la courbe
    public void drawCurve(Pane root) {
        root.getChildren().removeAll(curvePoints);
        curvePoints = this.courbeQuad(300); // Nombre de points pour dessiner la courbe
        for (Circle p : curvePoints) {
            // Utiliser un rayon plus petit pour les points de la courbe
            p.setRadius(1);
            p.setFill(this.couleur); // Changer la couleur pour différencier les points de la courbe
            root.getChildren().add(p);
        }
    }

    // Méthode pour supprimer la courbe du plan
    public void delete(Pane root) {
        root.getChildren().removeAll(curvePoints);
    }

    // Méthode pour changer la couleur de la courbe et redessiner la courbe avec la nouvelle couleur
    public void colorChange(Color coul, Pane root) {
        root.getChildren().removeAll(curvePoints);
        curvePoints = this.courbeQuad(500); // Nombre de points pour dessiner la courbe
        for (Circle p : curvePoints) {
            // Utiliser un rayon plus petit pour les points de la courbe
            p.setRadius(1);
            p.setFill(coul); // Changer la couleur pour différencier les points de la courbe
            root.getChildren().add(p);
        }
        this.setColor(coul);
    }
}

