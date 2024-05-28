package triangle;

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
public class MouseDragHandlerDelaunay implements EventHandler<MouseEvent> {

	private List<Circle> listePoint;
	private Pane root;
	private int indexPoint;

	public MouseDragHandlerDelaunay(List<Circle> listePoint, Pane root, int indexPoint) {
		this.listePoint = listePoint;
		this.root = root;
		this.indexPoint = indexPoint;
	}

	@Override
	/**
	 * La fonction handle gère le déplacement des points et la modification des
	 * courbes lorsqu'un point est déplacé
	 */
	public void handle(MouseEvent event) {

		// Permet de déplacer un point
		listePoint.get(indexPoint).setCursor(Cursor.CLOSED_HAND);
		listePoint.get(indexPoint).setCenterX(event.getX());
		listePoint.get(indexPoint).setCenterY(event.getY());

	}

}
