package first;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant de gérer la création de courbes et de points
 * 
 * @author Nicolas F
 *
 */
public class MouseClickHandler implements EventHandler<MouseEvent> {

	private List<QCurve> curves1;
	private List<QCurve> curves2;
	private List<Circle> points1;
	private List<Circle> points2;
	private Pane root1;
	private Pane root2;
	private boolean closeState;
	private Button clo;
	private Button del;
	private Color couleur= Color.BLUE;

	public MouseClickHandler(List<QCurve> curves1, List<QCurve> curves2, List<Circle> points1,
			List<Circle> points2, boolean closeState, Pane root1, Pane root2,Button clo, Button del, Color coul) {
		this.curves1 = curves1;
		this.curves2 = curves2;
		this.points1 = points1;
		this.points2 = points2;
		this.closeState = closeState;
		this.root1 = root1;
		this.root2 = root2;
		this.clo = clo;
		this.del = del;
		this.couleur= coul;
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
		for (Circle p : points1) {
			if (p.getBoundsInParent().contains(event.getX(), event.getY())) {
				return;
			}
		}

		// Si la souris n'est pas dans le plan, on n'ajoute pas de points
		double x = event.getX();
		double y = event.getY();

		if (!root1.contains(x, y)) {
			return;
		}

		// Création du point et ajout au dessin
		Circle point1 = new Circle(x, y, 7);
		point1.setFill(Color.RED);
		points1.add(point1);
		root1.getChildren().add(point1);
		
		Circle point2 = new Circle(x, y, 7);
		point2.setFill(Color.RED);
		points2.add(point2);
		root2.getChildren().add(point2);
		System.out.println(""+points1.size() + "   " + points2.size());
		
		int nombrePoint = points1.size();

		// Création du premier point de contrôle
		if (nombrePoint % 3 == 2 && nombrePoint < 4) {
			Circle pointControl1 = midle(points1.get(nombrePoint - 2), points1.get(nombrePoint - 1));
			pointControl1.setFill(Color.GREEN);
			points1.add(pointControl1);
			root1.getChildren().add(pointControl1);
			
			Circle pointControl2 = midle(points1.get(nombrePoint - 2), points1.get(nombrePoint - 1));
			pointControl2.setFill(Color.GREEN);
			points2.add(pointControl2);
			root2.getChildren().add(pointControl2);
			nombrePoint++;
			
		}

		// Création de la première courbe
		if (nombrePoint % 3 == 0 && nombrePoint < 4) {
			QCurve curve1 = new QCurve(points1.get(nombrePoint - 3).getCenterX(),
					points1.get(nombrePoint - 3).getCenterY(), points1.get(nombrePoint - 1).getCenterX(),
					points1.get(nombrePoint - 1).getCenterY(), points1.get(nombrePoint - 2).getCenterX(),
					points1.get(nombrePoint - 2).getCenterY(), couleur);
			curves1.add(curve1);
			curve1.drawCurve(root1);
			
			QCurve curve2 = new QCurve(points2.get(nombrePoint - 3).getCenterX(),
					points2.get(nombrePoint - 3).getCenterY(), points2.get(nombrePoint - 1).getCenterX(),
					points2.get(nombrePoint - 1).getCenterY(), points2.get(nombrePoint - 2).getCenterX(),
					points2.get(nombrePoint - 2).getCenterY(), couleur);
			curves2.add(curve2);
			curve2.drawCurve(root2);
		}

		// Création des courbes, sauf pour celle de fermeture
		if (nombrePoint >= 4 && nombrePoint % 2 == 0) {
			System.out.println(" \t >> " + nombrePoint);
			
			Circle pointControl1 = midle(points1.get(nombrePoint - 3), points1.get(nombrePoint - 1));
			pointControl1.setFill(Color.GREEN);
			points1.add(pointControl1);
			root1.getChildren().add(pointControl1);
			
			Circle pointControl2 = midle(points2.get(nombrePoint - 3), points2.get(nombrePoint - 1));
			pointControl2.setFill(Color.GREEN);
			points2.add(pointControl2);
			root2.getChildren().add(pointControl2);
			
			nombrePoint++;
			
			QCurve curve1 = new QCurve(points1.get(nombrePoint - 4).getCenterX(),
					points1.get(nombrePoint - 4).getCenterY(), points1.get(nombrePoint - 1).getCenterX(),
					points1.get(nombrePoint - 1).getCenterY(), points1.get(nombrePoint - 2).getCenterX(),
					points1.get(nombrePoint - 2).getCenterY(),couleur);
			curves1.add(curve1); // Ajoute la courbe à la liste des courbes
			curve1.drawCurve(root1); // Ajoute la courbe à la scène
			
			QCurve curve2 = new QCurve(points2.get(nombrePoint - 4).getCenterX(),
					points2.get(nombrePoint - 4).getCenterY(), points2.get(nombrePoint - 1).getCenterX(),
					points2.get(nombrePoint - 1).getCenterY(), points2.get(nombrePoint - 2).getCenterX(),
					points2.get(nombrePoint - 2).getCenterY(),couleur);
			curves2.add(curve2);
			curve2.drawCurve(root2);
		}
		
		if (nombrePoint > 1) {
			clo.setDisable(false);
		}
		if (nombrePoint > 0) {
			del.setDisable(false);
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
