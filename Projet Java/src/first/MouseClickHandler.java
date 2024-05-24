package first;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant de gérer la création de courbes et de points
 * 
 * @author Nicolas F
 *
 */
public class MouseClickHandler implements EventHandler<MouseEvent> {

    private List<QCurve> courbesDepart;
    private List<QCurve> courbesFin;
    private List<Circle> pointsDepart;
    private List<Circle> pointsFin;
    private Pane panneauDepart;
    private Pane panneauFin;
    private boolean etatFerme;
    private Button boutonFermer;
    private Button boutonSupprimer;
    private Color couleur = Color.BLUE;

    /**
     * Constructeur du gestionnaire de clics de souris.
     * 
     * @param courbesDepart Liste des courbes de départ.
     * @param courbesFin Liste des courbes de fin.
     * @param pointsDepart Liste des points de départ.
     * @param pointsFin Liste des points de fin.
     * @param etatFerme État de fermeture des formes.
     * @param panneauDepart Panneau contenant les points de départ.
     * @param panneauFin Panneau contenant les points de fin.
     * @param boutonFermer Bouton pour fermer les formes.
     * @param boutonSupprimer Bouton pour supprimer des points.
     * @param couleur Couleur des courbes.
     */
	public MouseClickHandler(List<QCurve> courbesDepart, List<QCurve> courbesFin, List<Circle> pointsDepart,
            List<Circle> pointsFin, boolean etatFerme, Pane panneauDepart, Pane panneauFin,
            Button boutonFermer, Button boutonSupprimer, Color couleur) {
		this.courbesDepart = courbesDepart;
		this.courbesFin = courbesFin;
		this.pointsDepart = pointsDepart;
		this.pointsFin = pointsFin;
		this.etatFerme = etatFerme;
		this.panneauDepart = panneauDepart;
		this.panneauFin = panneauFin;
		this.boutonFermer = boutonFermer;
		this.boutonSupprimer = boutonSupprimer;
		this.couleur = couleur;
}

	@Override
	/**
	 * La fonction handle permet de rajouter des points, si le périmètre est fermé
	 * la fonction ne fait rien, si la souris est au dessus d'un point préexistant
	 * la fonction ne fait rien <br>
	 * La fonction gère aussi la création de courbes
	 * @param event L'événement de clic de souris.
	 */
	public void handle(MouseEvent event) {
		// Le périmètre est fermé, la fonction ne fait rien
        if (etatFerme) {
            return;
        }

        // La souris est au-dessus d'un point existant, la fonction ne fait rien
        for (Circle point : pointsDepart) {
            if (point.getBoundsInParent().contains(event.getX(), event.getY())) {
                return;
            }
        }

        // Si la souris n'est pas dans le panneau, on n'ajoute pas de points
        double x = event.getX();
        double y = event.getY();

        if (!panneauDepart.contains(x, y)) {
            return;
        }

        // Création du point et ajout au dessin
        Circle pointDepart = new Circle(x, y, 7);
        pointDepart.setFill(Color.RED);
        pointsDepart.add(pointDepart);
        panneauDepart.getChildren().add(pointDepart);
        
        Circle pointFin = new Circle(x, y, 7);
        pointFin.setFill(Color.RED);
        pointsFin.add(pointFin);
        panneauFin.getChildren().add(pointFin);
        System.out.println("" + pointsDepart.size() + "   " + pointsFin.size());
        
        int nombrePoints = pointsDepart.size();


     // Création du premier point de contrôle
        if (nombrePoints % 3 == 2 && nombrePoints < 4) {
            Circle pointControleDepart = creerPointControle(pointsDepart.get(nombrePoints - 2), pointsDepart.get(nombrePoints - 1));
            pointControleDepart.setFill(Color.GREEN);
            pointsDepart.add(pointControleDepart);
            panneauDepart.getChildren().add(pointControleDepart);
            
            Circle pointControleFin = creerPointControle(pointsDepart.get(nombrePoints - 2), pointsDepart.get(nombrePoints - 1));
            pointControleFin.setFill(Color.GREEN);
            pointsFin.add(pointControleFin);
            panneauFin.getChildren().add(pointControleFin);
            nombrePoints++;
        }

        // Création de la première courbe
        if (nombrePoints % 3 == 0 && nombrePoints < 4) {
            QCurve courbeDepart = new QCurve(pointsDepart.get(nombrePoints - 3).getCenterX(),
                    pointsDepart.get(nombrePoints - 3).getCenterY(), pointsDepart.get(nombrePoints - 1).getCenterX(),
                    pointsDepart.get(nombrePoints - 1).getCenterY(), pointsDepart.get(nombrePoints - 2).getCenterX(),
                    pointsDepart.get(nombrePoints - 2).getCenterY(), couleur);
            courbesDepart.add(courbeDepart);
            courbeDepart.drawCurve(panneauDepart);
            
            QCurve courbeFin = new QCurve(pointsFin.get(nombrePoints - 3).getCenterX(),
                    pointsFin.get(nombrePoints - 3).getCenterY(), pointsFin.get(nombrePoints - 1).getCenterX(),
                    pointsFin.get(nombrePoints - 1).getCenterY(), pointsFin.get(nombrePoints - 2).getCenterX(),
                    pointsFin.get(nombrePoints - 2).getCenterY(), couleur);
            courbesFin.add(courbeFin);
            courbeFin.drawCurve(panneauFin);
        }

        // Création des courbes, sauf pour celle de fermeture
        if (nombrePoints >= 4 && nombrePoints % 2 == 0) {
            System.out.println(" \t >> " + nombrePoints);
            
            Circle pointControleDepart = creerPointControle(pointsDepart.get(nombrePoints - 3), pointsDepart.get(nombrePoints - 1));
            pointControleDepart.setFill(Color.GREEN);
            pointsDepart.add(pointControleDepart);
            panneauDepart.getChildren().add(pointControleDepart);
            
            Circle pointControleFin = creerPointControle(pointsFin.get(nombrePoints - 3), pointsFin.get(nombrePoints - 1));
            pointControleFin.setFill(Color.GREEN);
            pointsFin.add(pointControleFin);
            panneauFin.getChildren().add(pointControleFin);
            
            nombrePoints++;
            
            QCurve courbeDepart = new QCurve(pointsDepart.get(nombrePoints - 4).getCenterX(),
                    pointsDepart.get(nombrePoints - 4).getCenterY(), pointsDepart.get(nombrePoints - 1).getCenterX(),
                    pointsDepart.get(nombrePoints - 1).getCenterY(), pointsDepart.get(nombrePoints - 2).getCenterX(),
                    pointsDepart.get(nombrePoints - 2).getCenterY(), couleur);
            courbesDepart.add(courbeDepart); // Ajoute la courbe à la liste des courbes
            courbeDepart.drawCurve(panneauDepart); // Ajoute la courbe à la scène
            
            QCurve courbeFin = new QCurve(pointsFin.get(nombrePoints - 4).getCenterX(),
                    pointsFin.get(nombrePoints - 4).getCenterY(), pointsFin.get(nombrePoints - 1).getCenterX(),
                    pointsFin.get(nombrePoints - 1).getCenterY(), pointsFin.get(nombrePoints - 2).getCenterX(),
                    pointsFin.get(nombrePoints - 2).getCenterY(), couleur);
            courbesFin.add(courbeFin);
            courbeFin.drawCurve(panneauFin);
        }
        
        if (nombrePoints > 1) {
            boutonFermer.setDisable(false);
        }
        if (nombrePoints > 0) {
            boutonSupprimer.setDisable(false);
        }
    }


    /**
     * Création d'un point de contrôle qui se situe entre deux points, la courbe
     * associée sera une droite.
     * 
     * @param debut Le cercle représentant le point de départ de la courbe.
     * @param fin   Le cercle représentant le point de fin de la courbe.
     * @return Un point de contrôle pour la courbe avec debut comme début et fin
     *         comme fin.
     */
    private Circle creerPointControle(Circle debut, Circle fin) {
        double xControle = ((debut.getCenterX() + fin.getCenterX()) / 2);
        double yControle = ((debut.getCenterY() + fin.getCenterY()) / 2);
        Circle pointControle = new Circle(xControle, yControle, 5);
        return pointControle;
    }
}
