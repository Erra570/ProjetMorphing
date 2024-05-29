package first;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class QCurve {

	private double XDepart;
	private double YDepart;
	private double XControl;
	private double YControl;
	private double XFin;
	private double YFin;
	private List<Circle> curvePoints;
	private Color couleur;

	public QCurve(double xd, double yd, double xc, double yc, double xf, double yf, Color couleur) {
		this.XDepart = xd;
		this.YDepart = yd;
		this.XControl = xc;
		this.YControl = yc;
		this.XFin = xf;
		this.YFin = yf;
		this.curvePoints = new ArrayList<Circle>();
		this.couleur = couleur;
	}

	public Circle getPoint(double t) throws IllegalArgumentException {
		if (t < 0 || t > 1) {
			throw new IllegalArgumentException("t out of range 0 <= t <= 1");
		}
		double x = Math.pow(1 - t, 2) * this.XDepart + 2 * (1 - t) * t * this.XControl + Math.pow(t, 2) * this.XFin;
		double y = Math.pow(1 - t, 2) * this.YDepart + 2 * (1 - t) * t * this.YControl + Math.pow(t, 2) * this.YFin;
		return new Circle(x, y, 5);
	}

	public List<Circle> courbeQuad(int step) {
		curvePoints.clear(); // Effacer les points précédents
		for (int i = 0; i <= step; i++) {
			double t = (double) i / step;
			getPoint(t).getStyleClass().add("pts");
			curvePoints.add(getPoint(t));
		}
		return curvePoints;
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

	public void update(Circle depart, Circle controle, Circle fin, Pane root) {
		this.setXControl(controle.getCenterX()); 
		this.setYControl(controle.getCenterY()); 
		this.setXDepart(depart.getCenterX());
		this.setYDepart(depart.getCenterY());
		this.setXFin(fin.getCenterX());
		this.setYFin(fin.getCenterY());
		this.drawCurve(root);
	}

	public void drawCurve(Pane root) {
		root.getChildren().removeAll(curvePoints);
		curvePoints = this.courbeQuad(300); // Nombre de points pour dessiner la courbe
		for (Circle p : curvePoints) {
			// Utiliser un rayon plus petit pour les points de la
			// courbe
			p.setRadius(1);
			p.setFill(this.couleur); // Changer la couleur pour différencier les points de la courbe
			root.getChildren().add(p);
		}
	}
	
	public void delete(Pane root) {
		root.getChildren().removeAll(curvePoints);
	}
	
	public void colorChange(Color coul, Pane root) {
		root.getChildren().removeAll(curvePoints);
		curvePoints = this.courbeQuad(500); // Nombre de points pour dessiner la courbe
		for (Circle p : curvePoints) {
			// Utiliser un rayon plus petit pour les points de la
			// courbe
			p.setRadius(1);
			p.setFill(coul); // Changer la couleur pour différencier les points de la courbe
			root.getChildren().add(p);
		}
		this.setColor(coul);
	}
}