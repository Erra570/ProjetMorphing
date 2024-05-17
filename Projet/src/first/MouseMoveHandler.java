package first;

import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Classe permettant d'obtenir des infos sur les points ainsi que d'appliquer l'event Drag sur tout les points
 * <br>
 * Elle gère aussi quelque option de style pour la souris
 * @author Nicolas F
 *
 */
public class MouseMoveHandler implements EventHandler<MouseEvent> {

	private List<QuadCurve> curves;
	private List<Circle> points;
	//private List<QuadCurve> curves2;
	//private List<Circle> points2;
	private boolean closeState;
	private Pane root;
	private int ID;

	public MouseMoveHandler(List<QuadCurve> curves, List<Circle> points, boolean closeState, Pane root, int ID) {
		this.curves = curves;
		this.points = points;
		//this.curves2 = curves2;
		//this.points2 = points2;
		this.closeState = closeState;
		this.root = root;
		this.ID = ID;
	}

	@Override
	/**
	 * La fonction handle permet de gérer l'event Drag sur tout les points, passer
	 * la souris par dessus un point ou une courbe affiche son idex dans la console.
	 * <br>
	 * Passer la souris par dessus un point change la souris, elle change encore lorsqu'un point est déplacé
	 */
	public void handle(MouseEvent event) {
		for (Circle p : points) {
			if (p.getBoundsInParent().contains(event.getX(), event.getY())) {
				p.setCursor(Cursor.HAND);
				System.out.println("" + points.indexOf(p)+ " "+ ID);
				MouseDragHandler dragHandler = new MouseDragHandler(curves, points, closeState, points.indexOf(p));
				p.setOnMouseDragged(dragHandler);
			}
		}

		for (QuadCurve c : curves) {
			if (c.getBoundsInParent().contains(event.getX(), event.getY())) {
				int index = curves.indexOf(c);
				System.out.println("Index de la ligne : " + index + " "+ ID);
			}
		}
		
		root.setCursor(Cursor.DEFAULT);

	}

}
