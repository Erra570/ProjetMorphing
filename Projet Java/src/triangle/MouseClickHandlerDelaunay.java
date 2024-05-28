package triangle;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MouseClickHandlerDelaunay implements EventHandler<MouseEvent> {
	private ArrayList<Circle> pointGraph;
	private Pane root;
	private Color couleur;
	private Button del;

	public MouseClickHandlerDelaunay(ArrayList<Circle> pointGraph, Pane root,Color couleur, Button del) {
		this.pointGraph = pointGraph;
		this.root = root;
		this.couleur = couleur;
		this.del = del;
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
		point.setFill(couleur);
		pointGraph.add(point);
		root.getChildren().add(point);
		del.setDisable(false);
		
	}
	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
		if (pointGraph.size() != 0) {
			for (Circle p : pointGraph) {
				root.getChildren().remove(p);
				p.setFill(couleur);
				root.getChildren().add(p);
			}
		}
	}

}
