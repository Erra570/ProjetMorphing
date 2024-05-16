package first;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 *Classe permettant de gérer la création de courbes et de points 
 * @author Nicolas F
 *
 */
public class MouseClickHandler implements EventHandler<MouseEvent> {

	private List<QuadCurve> curves;
	private List<Circle> points;
	private Pane root;
	private boolean closeState;

	public MouseClickHandler(List<QuadCurve> curves, List<Circle> points, boolean closeState, Pane root) {
		this.curves = curves;
		this.points = points;
		this.closeState = closeState;
		this.root = root;
	}

	@Override
	/**
	 * La fonction handle permet de rajouter des points, si le périmètre est fermé
	 * la fonction ne fait rien, si la souris est au dessus d'un point préexistant
	 * la fonction ne fait rien <br>
	 * La fonction gère aussi la création de courbes
	 */
	public void handle(MouseEvent event) {
		// Le périmètre est férmé la fonction ne fait rien
		if (closeState) {
			return;
		}

		// La souris est au dessus d'un point préexistant, la fonction ne fait rien
		for (Circle p : points) {
			if (p.getBoundsInParent().contains(event.getX(), event.getY())) {
				return;
			}
		}

		// Création du point et ajout au dessin
		double x = event.getX();
		double y = event.getY();
		Circle point = new Circle(x, y, 5);
		point.setFill(Color.RED);
		points.add(point);
		root.getChildren().add(point);
		int nombrePoint = points.size();

		// Création du premier point de contrôle
		if (nombrePoint % 3 == 2 && nombrePoint < 4) {
			Circle pointControl = midle(points.get(nombrePoint - 2), points.get(nombrePoint - 1));
			pointControl.setFill(Color.GREEN);
			points.add(pointControl);
			root.getChildren().add(pointControl);
			nombrePoint++;
		}

		// Création de la première courbe
		if (nombrePoint % 3 == 0 && nombrePoint < 4) {
			QuadCurve curve = new QuadCurve(points.get(nombrePoint - 3).getCenterX(),
					points.get(nombrePoint - 3).getCenterY(), points.get(nombrePoint - 1).getCenterX(),
					points.get(nombrePoint - 1).getCenterY(), points.get(nombrePoint - 2).getCenterX(),
					points.get(nombrePoint - 2).getCenterY());
			curve.setStroke(Color.BLACK);
			curve.setFill(null);
			curves.add(curve);
			root.getChildren().add(curve);
		}

		// Création des courbes, sauf pour celle de fermeture
		if (nombrePoint >= 4 && nombrePoint % 2 == 0) {
			System.out.println(" \t >> " + nombrePoint);
			Circle pointControl = midle(points.get(nombrePoint - 3), points.get(nombrePoint - 1));
			pointControl.setFill(Color.GREEN);
			points.add(pointControl);
			root.getChildren().add(pointControl);
			nombrePoint++;
			QuadCurve curve = new QuadCurve(points.get(nombrePoint - 4).getCenterX(),
					points.get(nombrePoint - 4).getCenterY(), points.get(nombrePoint - 1).getCenterX(),
					points.get(nombrePoint - 1).getCenterY(), points.get(nombrePoint - 2).getCenterX(),
					points.get(nombrePoint - 2).getCenterY());
			curve.setStroke(Color.BLACK);
			curve.setFill(null);
			curves.add(curve); // Ajoute la courbe à la liste des courbes
			root.getChildren().add(curve); // Ajoute la courbe à la scène
		}
	}

	/**
	 * Création d'un point de controle qui se situe entre deux points, la courbe
	 * associée sera une droite
	 * 
	 * @param start Le cercle représentant le point de départ de la courbe.
	 * @param end   Le cercle représentant le point de fin de la courbe.
	 * @return Un point de controle pour la courbe avec start comme début et end
	 *         comme fin
	 */
	private Circle midle(Circle start, Circle end) {
		double xControl = ((start.getCenterX() + end.getCenterX()) / 2);
		double yControl = ((start.getCenterY() + end.getCenterY()) / 2);
		Circle pointControl = new Circle(xControl, yControl, 5);
		return pointControl;
	}
}
