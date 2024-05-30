package triangle;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MouseClickHandlerDelaunay implements EventHandler<MouseEvent> {
	private ArrayList<Circle> pointGraph1;
	private Pane root1;
	private ArrayList<Circle> pointGraph2;
	private Pane root2;
	private Color couleur;
	private boolean modeState = false;
	private Button del;
	private Button start;

	public MouseClickHandlerDelaunay(ArrayList<Circle> pointGraph1, Pane root1, ArrayList<Circle> pointGraph2,
			Pane root2, Color couleur, boolean modeState,Button del,Button start) {
		this.pointGraph1 = pointGraph1;
		this.root1 = root1;
		this.pointGraph2 = pointGraph2;
		this.root2 = root2;
		this.couleur = couleur;
		this.modeState = modeState;
		this.del = del;
		this.start = start;
	}

	@Override
	public void handle(MouseEvent event) {
		// La souris est au dessus d'un point pr�existant, la fonction ne fait rien
		if (pointGraph1.size() != 0) {
			for (Circle p : pointGraph1) {
				if (p.getBoundsInParent().contains(event.getX(), event.getY())) {
					return;
				}
			}
		}
		if (!modeState) {
			return;
		}

		// Si la souris n'est pas dans le plan, on n'ajoute pas de points
		double x = event.getX();
		double y = event.getY();

		if (!root1.contains(x, y)) {
			return;
		}

		// Cr�ation du point et ajout au dessin
		Circle point1 = new Circle(x, y, 7);
		point1.setFill(couleur);
		pointGraph1.add(point1);
		root1.getChildren().add(point1);

		Circle point2 = new Circle(x, y, 7);
		point2.setFill(couleur);
		pointGraph2.add(point2);
		root2.getChildren().add(point2);
		del.setDisable(false);
		start.setDisable(false);
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
		if (pointGraph1.size() != 0) {
			for (Circle p : pointGraph1) {
				root1.getChildren().remove(p);
				p.setFill(couleur);
				root1.getChildren().add(p);
			}
			for (Circle p : pointGraph2) {
				root2.getChildren().remove(p);
				p.setFill(couleur);
				root2.getChildren().add(p);
			}
		}
	}

}