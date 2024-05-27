package triangle;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MouseClickHandlerDelaunay implements EventHandler<MouseEvent> {
	private ArrayList<Circle> pointGraph;
	private Pane root;

	public MouseClickHandlerDelaunay(ArrayList<Circle> pointGraph, Pane root) {
		this.pointGraph = pointGraph;
		this.root = root;
	}

	@Override
	public void handle(MouseEvent event) {
		// La souris est au dessus d'un point préexistant, la fonction ne fait rien
		if (pointGraph.size() != 0) {
			for (Circle p : pointGraph) {
				if (p.getBoundsInParent().contains(event.getX(), event.getY())) {
					return;
				}
			}
		}

		// Si la souris n'est pas dans le plan, on n'ajoute pas de points
		double x = event.getX();
		double y = event.getY();

		if (!root.contains(x, y)) {
			return;
		}

		// Création du point et ajout au dessin
		Circle point = new Circle(x, y, 7);
		point.setFill(Color.RED);
		pointGraph.add(point);
		root.getChildren().add(point);
	}

}
