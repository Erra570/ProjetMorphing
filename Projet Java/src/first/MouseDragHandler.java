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

    private List<QCurve> courbes;
    private List<Circle> points;
    private boolean etatFerme;
    private int indexPoint;
    private Pane panneau;

    /**
     * Constructeur du gestionnaire de curseur.
     * 
     * @param courbes Liste des courbes.
     * @param points Liste des points.
     * @param etatFerme État de fermeture des formes.
     * @param indexPoint Index du point à déplacer.
     * @param panneau Panneau contenant les points.
     */
	public MouseDragHandler(List<QCurve> courbes, List<Circle> points, boolean etatFerme, int indexPoint, Pane panneau) {
        this.courbes = courbes;
        this.points = points;
        this.etatFerme = etatFerme;
        this.indexPoint = indexPoint;
        this.panneau = panneau;
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

        // Gestion des courbes en bougeant un sommet sauf le premier et le dernier
        if (indexPoint >= 1 && indexPoint % 2 == 1 && indexPoint < points.size() - 3) {
            if (indexPoint == 1) {
                courbes.get(indexPoint / 2).update(points.get(indexPoint - 1), points.get(indexPoint + 1),
                        points.get(indexPoint), panneau);
                courbes.get((indexPoint / 2) + 1).update(points.get(indexPoint), points.get(indexPoint + 3),
                        points.get(indexPoint + 2), panneau);
            } else {
                courbes.get(indexPoint / 2).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
                        points.get(indexPoint), panneau);
                courbes.get((indexPoint / 2) + 1).update(points.get(indexPoint), points.get(indexPoint + 3),
                        points.get(indexPoint + 2), panneau);
            }
        }

        // Gestion des courbes en bougeant un point de contrôle
        if (indexPoint != 0 && indexPoint % 2 == 0) {
            if (!etatFerme) { // Le périmètre est ouvert
                if (indexPoint == 2) {
                    courbes.get((indexPoint / 2) - 1).update(points.get(indexPoint - 2), points.get(indexPoint),
                            points.get(indexPoint - 1), panneau);
                } else {
                    courbes.get((indexPoint / 2) - 1).update(points.get(indexPoint - 3), points.get(indexPoint),
                            points.get(indexPoint - 1), panneau);
                }
            } else { // Le périmètre est fermé
                if (indexPoint == 2) {
                    courbes.get((indexPoint / 2) - 1).update(points.get(indexPoint - 2), points.get(indexPoint),
                            points.get(indexPoint - 1), panneau);
                } else {
                    courbes.get((indexPoint / 2) - 1).update(points.get(indexPoint - 3), points.get(indexPoint),
                            points.get(indexPoint - 1), panneau);
                }
            }
        }

        // Gestion de la courbe associée au dernier point de contrôle
        if (indexPoint == points.size() - 1 && etatFerme) {
            courbes.get(courbes.size() - 1).update(points.get(indexPoint - 2), points.get(indexPoint), points.get(0),
                    panneau);
        }

        // Gestion des courbes associées au premier sommet
        if (indexPoint == 0) {
            if (etatFerme) { // Le périmètre est fermé
                courbes.get(0).update(points.get(0), points.get(2), points.get(1), panneau);
                courbes.get(courbes.size() - 1).update(points.get(points.size() - 3), points.get(points.size() - 1),
                        points.get(0), panneau);
            } else { // Le périmètre est ouvert
                courbes.get(0).update(points.get(0), points.get(2), points.get(1), panneau);
            }
        }

        // Gestion des courbes associées au dernier sommet avec le périmètre fermé
        if (indexPoint == points.size() - 3 && etatFerme) {
            courbes.get(courbes.size() - 1).update(points.get(indexPoint), points.get(indexPoint + 2), points.get(0),
                    panneau);
            courbes.get(courbes.size() - 2).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
                    points.get(indexPoint), panneau);
        }

        // Gestion de la courbe associée au dernier sommet avec le périmètre ouvert
        if (indexPoint == points.size() - 2 && !etatFerme && indexPoint != 0) {
            if (indexPoint != 1) {
                courbes.get(courbes.size() - 1).update(points.get(indexPoint - 2), points.get(indexPoint + 1),
                        points.get(indexPoint), panneau);
            } else {
                courbes.get(0).update(points.get(0), points.get(2), points.get(1), panneau);
            }
        }
    }
}