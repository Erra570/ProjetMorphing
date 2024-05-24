package first;

import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
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

    private List<QCurve> courbes;
    private List<Circle> points;
    private boolean etatFerme;
    private Pane panneau;
    private int identifiant;

	public MouseMoveHandler(List<QCurve> courbes, List<Circle> points, boolean etatFerme, Pane panneau, int identifiant) {
        this.courbes = courbes;
        this.points = points;
        this.etatFerme = etatFerme;
        this.panneau = panneau;
        this.identifiant = identifiant;
    }

	@Override
	/**
	 * La fonction handle permet de gérer l'event Drag sur tout les points, passer
	 * la souris par dessus un point ou une courbe affiche son idex dans la console.
	 * <br>
	 * Passer la souris par dessus un point change la souris, elle change encore lorsqu'un point est déplacé
	 */
	public void handle(MouseEvent event) {
		for (Circle point : points) {
            if (point.getBoundsInParent().contains(event.getX(), event.getY())) {
                point.setCursor(Cursor.HAND);
                System.out.println("" + points.indexOf(point) + " " + identifiant);
                MouseDragHandler gestionnaireGlisse = new MouseDragHandler(courbes, points, etatFerme, points.indexOf(point), panneau);
                point.setOnMouseDragged(gestionnaireGlisse);
            }
        }
        panneau.setCursor(Cursor.DEFAULT);
    }

}