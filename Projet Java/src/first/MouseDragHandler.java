package first;


import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * Classe permettant de gérer le déplacement des points et l'évolution des
 * courbes quand un point est déplacé
 * 
 * @author Nicolas F
 *
 */
public class MouseDragHandler implements EventHandler<MouseEvent> {

	private List<QuadCurve> curves;
	private List<Circle> points;
	private boolean closeState;
	private int indexPoint;

	public MouseDragHandler(List<QuadCurve> curves, List<Circle> points, boolean closeState, int indexPoint) {
		this.curves = curves;
		this.points = points;
		this.closeState = closeState;
		this.indexPoint = indexPoint;
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
				updateCurve(curves.get(indexPoint / 2), points.get(indexPoint - 1), points.get(indexPoint + 1),
						points.get(indexPoint));
				updateCurve(curves.get((indexPoint / 2) + 1), points.get(indexPoint), points.get(indexPoint + 3),
						points.get(indexPoint + 2));
			} else {
				updateCurve(curves.get(indexPoint / 2), points.get(indexPoint - 2), points.get(indexPoint + 1),
						points.get(indexPoint));
				updateCurve(curves.get((indexPoint / 2) + 1), points.get(indexPoint), points.get(indexPoint + 3),
						points.get(indexPoint + 2));
			}
		}

		// Gestion des courbes en bougeant un points de contrôle
		if (indexPoint != 0 && indexPoint % 2 == 0) {
			if (!closeState) {// Le périmètre est ouvert
				if (indexPoint == 2) {
					updateCurve(curves.get((indexPoint / 2) - 1), points.get(indexPoint - 2), points.get(indexPoint),
							points.get(indexPoint - 1));
				} else {
					updateCurve(curves.get((indexPoint / 2) - 1), points.get(indexPoint - 3), points.get(indexPoint),
							points.get(indexPoint - 1));
				}

			} else { // Le périmètre est fermé
				if (indexPoint == 2) {
					updateCurve(curves.get((indexPoint / 2) - 1), points.get(indexPoint - 2), points.get(indexPoint),
							points.get(indexPoint - 1));
				} else {
					updateCurve(curves.get((indexPoint / 2) - 1), points.get(indexPoint - 3), points.get(indexPoint),
							points.get(indexPoint - 1));

				}
			}
		}

		// Gestion de la courbe associée au dernier point de contrôle
		if (indexPoint == points.size() - 1 && closeState) {
			updateCurve(curves.get(curves.size() - 1), points.get(indexPoint - 2), points.get(indexPoint),
					points.get(0));
		}

		// Gestion des courbes associées au premier sommet
		if (indexPoint == 0) {
			if (closeState) {// Le périmètre est fermé
				updateCurve(curves.get(0), points.get(0), points.get(2), points.get(1));
				updateCurve(curves.get(curves.size() - 1), points.get(points.size() - 3), points.get(points.size() - 1),
						points.get(0));
			} else {// Le périmètre est ouvert
				updateCurve(curves.get(0), points.get(0), points.get(2), points.get(1));
			}
		}

		// Gestion des courbes associées au dernier sommet avec le périmètre fermé
		if (indexPoint == points.size() - 3 && closeState) {

			updateCurve(curves.get(curves.size() - 1), points.get(indexPoint), points.get(indexPoint + 2),
					points.get(0));
			updateCurve(curves.get(curves.size() - 2), points.get(indexPoint - 2), points.get(indexPoint + 1),
					points.get(indexPoint));
		}

		// Gestion de la courbe associée au dernier sommet avec le périmètre ouvert
		if (indexPoint == points.size() - 2 && !closeState) {
			if (indexPoint != 1) {
				updateCurve(curves.get(curves.size() - 1), points.get(indexPoint - 2), points.get(indexPoint + 1),
						points.get(indexPoint));
			} else {
				updateCurve(curves.get(0), points.get(0), points.get(2), points.get(1));
			}

		}

	}

	/**
	 * Met à jour les propriétés d'une courbe
	 *
	 * @param curve   La courbe à mettre à jour.
	 * @param start   Le cercle représentant le point de départ de la courbe.
	 * @param control Le cercle représentant le point de contrôle de la courbe.
	 * @param end     Le cercle représentant le point de fin de la courbe.
	 */
	private void updateCurve(QuadCurve curve, Circle start, Circle control, Circle end) {
		curve.setStartX(start.getCenterX());
		curve.setStartY(start.getCenterY());
		curve.setEndX(end.getCenterX());
		curve.setEndY(end.getCenterY());
		curve.setControlX(control.getCenterX());
		curve.setControlY(control.getCenterY());
	}

}
