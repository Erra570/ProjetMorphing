package first;


import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Classe permettant de gérer le déplacement des points et l'évolution des
 * courbes quand un point est déplacé
 * 
 * @author Nicolas F
 *
 */
public class MouseDragHandler implements EventHandler<MouseEvent> {

	private List<QCurve> curves;
	private List<Circle> points;
	private boolean closeState;
	private int indexPoint;
	private Pane root;

	public MouseDragHandler(List<QCurve> curves, List<Circle> points, boolean closeState, int indexPoint, Pane root) {
		this.curves = curves;
		this.points = points;
		this.closeState = closeState;
		this.indexPoint = indexPoint;
		this.root = root;
	}

	@Override
	/**
	 * La fonction handle gère le déplacement des points et la modification des
	 * courbes lorsqu'un point est déplacé
	 */
	public void handle(MouseEvent event) {

		// Permet de déplacer un point
		points.get(indexPoint).setCursor(Cursor.CLOSED_HAND);
		points.get(indexPoint).setCenterX(event.getX());
		points.get(indexPoint).setCenterY(event.getY());

		// Gestion des courbes en bougeant un sommet sommet sauf le premier et le
		// dernier
		if (indexPoint >= 1 && indexPoint % 2 == 1 && indexPoint < points.size() - 3) {
			if (indexPoint == 1) {
				curves.get(indexPoint / 2).update(points.get(indexPoint - 1), points.get(indexPoint + 1),
						points.get(indexPoint), root);
				curves.get((indexPoint / 2) + 1).update(points.get(indexPoint), points.get(indexPoint + 3),
						points.get(indexPoint + 2), root);
			} else {
				curves.get(indexPoint / 2).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
						points.get(indexPoint), root);
				curves.get((indexPoint / 2) + 1).update(points.get(indexPoint), points.get(indexPoint + 3),
						points.get(indexPoint + 2), root);
			}
		}

		// Gestion des courbes en bougeant un points de contrôle
		if (indexPoint != 0 && indexPoint % 2 == 0) {
			if (!closeState) {// Le périmètre est ouvert
				if (indexPoint == 2) {
					curves.get((indexPoint / 2) - 1).update(points.get(indexPoint - 2), points.get(indexPoint),
							points.get(indexPoint - 1), root);
				} else {
					curves.get((indexPoint / 2) - 1).update(points.get(indexPoint - 3), points.get(indexPoint),
							points.get(indexPoint - 1), root);
				}

			} else { // Le périmètre est fermé
				if (indexPoint == 2) {
					curves.get((indexPoint / 2) - 1).update(points.get(indexPoint - 2), points.get(indexPoint),
							points.get(indexPoint - 1), root);
				} else {
					curves.get((indexPoint / 2) - 1).update(points.get(indexPoint - 3), points.get(indexPoint),
							points.get(indexPoint - 1), root);

				}
			}
		}

		// Gestion de la courbe associée au dernier point de contrôle
		if (indexPoint == points.size() - 1 && closeState) {
			curves.get(curves.size() - 1).update(points.get(indexPoint - 2), points.get(indexPoint), points.get(0),
					root);
		}

		// Gestion des courbes associées au premier sommet
		if (indexPoint == 0) {
			if (closeState) {// Le périmètre est fermé
				curves.get(0).update(points.get(0), points.get(2), points.get(1), root);
				curves.get(curves.size() - 1).update(points.get(points.size() - 3), points.get(points.size() - 1),
						points.get(0), root);
			} else {// Le périmètre est ouvert
				curves.get(0).update(points.get(0), points.get(2), points.get(1), root);
			}
		}

		// Gestion des courbes associées au dernier sommet avec le périmètre fermé
		if (indexPoint == points.size() - 3 && closeState) {

			curves.get(curves.size() - 1).update(points.get(indexPoint), points.get(indexPoint + 2), points.get(0),
					root);
			curves.get(curves.size() - 2).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
					points.get(indexPoint), root);
		}

		// Gestion de la courbe associée au dernier sommet avec le périmètre ouvert
		if (indexPoint == points.size() - 2 && !closeState && indexPoint != 0) {
			if (indexPoint != 1) {
				curves.get(curves.size() - 1).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
						points.get(indexPoint), root);
			} else {
				curves.get(0).update(points.get(0), points.get(2), points.get(1), root);
			}

		}

	}


}