package first;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de créer des courbes pour les formes arrondies
 * @author Nicolas F
 *
 */
public class FormesArrondies extends Application {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private List<QuadCurve> curves = new ArrayList<>(); // liste de courbes
	private List<Circle> points = new ArrayList<>(); // liste de points
	private Pane root;
	private boolean closeState = false; // etat de fermeture du périmètre
	private Scene scene;

	@Override
	public void start(Stage primaryStage) {
		root = new Pane();
		scene = new Scene(root, WIDTH, HEIGHT);

		MouseClickHandler clickHandler = new MouseClickHandler(curves, points, closeState, root);
		scene.setOnMouseClicked(clickHandler);

		MouseMoveHandler moveHandler = new MouseMoveHandler(curves, points, closeState, scene);
		scene.setOnMouseMoved(moveHandler);

		Button del = new Button("Delete last");
		del.setOnAction(event -> delete(curves, points)); // Définir le gestionnaire d'événements
		root.getChildren().add(del);

		Button clo = new Button("Close");
		clo.setOnAction(event -> close()); // Définir le gestionnaire d'événements
		root.getChildren().add(clo);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Formes Arrondies");
		primaryStage.show();
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

	/**
	 * Permet de supprimer le dernier point placé, ainsi que la courbe associé si le
	 * point est le point de fin de la courbe, supprime aussi le point de contrôle
	 * associé à la courbe
	 * 
	 * @param curves liste des courbes
	 * @param points liste des points
	 */
	private void delete(List<QuadCurve> curves, List<Circle> points) {
		if (closeState) {
			root.getChildren().remove(curves.remove(curves.size() - 1));
			root.getChildren().remove(points.remove(points.size() - 1));
			closeState = false;

			MouseClickHandler clickHandler = new MouseClickHandler(curves, points, closeState, root);
			scene.setOnMouseClicked(clickHandler);
			MouseMoveHandler moveHandler = new MouseMoveHandler(curves, points, closeState, scene);
			scene.setOnMouseMoved(moveHandler);

		} else {
			if (!curves.isEmpty()) {
				root.getChildren().remove(curves.remove(curves.size() - 1));
			}
			if (!points.isEmpty()) {
				root.getChildren().remove(points.remove(points.size() - 1));
			}
			if (!points.isEmpty()) {
				root.getChildren().remove(points.remove(points.size() - 1));
			}
		}
		System.out.println("" + points.size());
	}

	/**
	 * Permet de fermé le graphe en reliant le dernier point au premier
	 */
	private void close() {
		Circle pointControl = midle(points.get(points.size() - 2), points.get(0));
		pointControl.setFill(Color.GREEN);
		points.add(pointControl);
		root.getChildren().add(pointControl);
		QuadCurve curve = new QuadCurve(points.get(points.size() - 3).getCenterX(),
				points.get(points.size() - 3).getCenterY(), points.get(points.size() - 1).getCenterX(),
				points.get(points.size() - 1).getCenterY(), points.get(0).getCenterX(), points.get(0).getCenterY());
		curve.setStroke(Color.BLACK);
		curve.setFill(null);
		curves.add(curve); // Ajoute la courbe à la liste des courbes
		root.getChildren().add(curve); // Ajoute la courbe à la scène
		closeState = true;

		MouseClickHandler clickHandler = new MouseClickHandler(curves, points, closeState, root);
		scene.setOnMouseClicked(clickHandler);
		MouseMoveHandler moveHandler = new MouseMoveHandler(curves, points, closeState, scene);
		scene.setOnMouseMoved(moveHandler);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
