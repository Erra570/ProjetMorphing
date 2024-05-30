package triangle;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * La classe Delaunay représente un algorithme de triangulation de Delaunay.
 * Elle permet de générer une triangulation à partir d'un ensemble de points
 * dans un plan.
 */
public class Delaunay {

    private static final Point POINT1 = new Point(350, -1000);
    private static final Point POINT2 = new Point(-750, 750);
    private static final Point POINT3 = new Point(1350, 700);
    private static final Point POINTA = new Point(0, 0);
    private static final Point POINTB = new Point(0, 500);
    private static final Point POINTC = new Point(500, 0);
    private static final Point POINTD = new Point(500, 500);
    private ArrayList<Triangle> listeTriangle = new ArrayList<Triangle>();
    private Triangle superTriangle;
    private ArrayList<Circle> pointGraph1;
    private Color couleur;

    /**
     * Constructeur de la classe Delaunay.
     * @param root1         Le conteneur graphique où seront placés les points.
     * @param root2         Le conteneur graphique où seront tracés les triangles.
     * @param pointGraph1   La liste des cercles représentant les points.
     * @param pointGraph2   La liste des cercles représentant les points après transformation.
     * @param couleur       La couleur des triangles.
     * @param modeState     L'état du mode (original ou transformé).
     * @param del           Le bouton pour supprimer le dernier point.
     * @param start         Le bouton pour démarrer la transformation.
     */
    public Delaunay(Pane root1, Pane root2, ArrayList<Circle> pointGraph1, ArrayList<Circle> pointGraph2, Color couleur, boolean modeState, Button del, Button start) {
        this.couleur = couleur;
        this.pointGraph1 = pointGraph1;
        MouseClickHandlerDelaunay clickHandler = new MouseClickHandlerDelaunay(pointGraph1, root1, pointGraph2, root2, this.couleur, modeState, del, start);
        root1.setOnMouseClicked(clickHandler);
    }

    /**
     * Récupère la liste des cercles représentant les points.
     * @return La liste des cercles représentant les points.
     */
    public ArrayList<Circle> getPGraphe() {
        return this.pointGraph1;
    }

    /**
     * Récupère la liste des triangles générés.
     * @return La liste des triangles générés.
     */
    public ArrayList<Triangle> getLTriangle() {
        return this.listeTriangle;
    }

    /**
     * Initialise la triangulation à partir des points et les affiche graphiquement.
     * @param root Le conteneur graphique où seront tracés les triangles.
     */
    public void initTriang(Pane root) {
        try {
            ArrayList<Point> listePoint = new ArrayList<Point>();
            for (int i = 0; i < pointGraph1.size(); i++) {
                listePoint.add(new Point(pointGraph1.get(i).getCenterX(), pointGraph1.get(i).getCenterY()));
            }
            listePoint.add(POINTA);
            listePoint.add(POINTB);
            listePoint.add(POINTC);
            listePoint.add(POINTD);
            listeTriangle = triangulation(listePoint);
            ArrayList<Line> listeLigne = affichageTriangle(listeTriangle);
            root.getChildren().addAll(listeLigne);
            System.out.println("fini");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime le dernier point ajouté et met à jour l'interface graphique.
     * @param root  Le conteneur graphique où sont affichés les points.
     * @param del   Le bouton de suppression de points.
     * @param start Le bouton de démarrage de transformation.
     */
    public void deleteLastPoint(Pane root, Button del, Button start) {
        if (!this.getPGraphe().isEmpty()) {
            root.getChildren().remove(this.getPGraphe().size() - 1);
            this.getPGraphe().remove(this.getPGraphe().size() - 1);
            if (this.getPGraphe().size() == 0) {
                del.setDisable(true);
                start.setDisable(true);
            }
        }
    }

    /**
     * Effectue la triangulation de Delaunay à partir d'une liste de points.
     * @param listePoint La liste des points à trianguler.
     * @return La liste des triangles générés.
     * @throws Exception Une exception en cas d'erreur de triangulation.
     */
    public ArrayList<Triangle> triangulation(ArrayList<Point> listePoint) throws Exception {
        superTriangle = new Triangle(POINT1, POINT2, POINT3);
        listeTriangle.add(superTriangle);
        for (int i = 0; i < listePoint.size(); i++) {
            listeTriangle = ajoutTriangle(listePoint.get(i), listeTriangle);
        }
        listeTriangle = epurationTriangle(listeTriangle);
        return listeTriangle;
    }

    /**
     * Ajoute un triangle à la liste des triangles en prenant en compte un nouveau point.
     * @param point           Le nouveau point à intégrer.
     * @param listeTriangle   La liste des triangles actuelle.
     * @return La liste mise à jour des triangles.
     * @throws Exception Une exception en cas d'erreur de triangulation.
     */
    public ArrayList<Triangle> ajoutTriangle(Point point, ArrayList<Triangle> listeTriangle) throws Exception {
        ArrayList<Cote> listeCote = new ArrayList<Cote>();
        ArrayList<Triangle> nouvelleListeTriangle = new ArrayList<Triangle>();

        for (int i = 0; i < listeTriangle.size(); i++) {
            if (listeTriangle.get(i).getCercle().contient(point)) {
                listeCote.add(new Cote(listeTriangle.get(i).getPoint_1(), listeTriangle.get(i).getPoint_2()));
                listeCote.add(new Cote(listeTriangle.get(i).getPoint_2(), listeTriangle.get(i).getPoint_3()));
                listeCote.add(new Cote(listeTriangle.get(i).getPoint_3(), listeTriangle.get(i).getPoint_1()));
            } else {
                nouvelleListeTriangle.add(listeTriangle.get(i));
            }
        }
        listeCote = coteUnique(listeCote);
        for (int k = 0; k < listeCote.size(); k++) {
            nouvelleListeTriangle.add(new Triangle(listeCote.get(k).getDebut(), listeCote.get(k).getFin(), point));
        }
        return nouvelleListeTriangle;
    }

    /**
     * Élimine les côtés de triangles redondants.
     * @param listeCote La liste des côtés à purger.
     * @return La liste des côtés sans doublons.
     */
     public ArrayList<Cote> coteUnique(ArrayList<Cote> listeCote) {
         ArrayList<Cote> listeCoteUnique = new ArrayList<Cote>();
         for (int i = 0; i < listeCote.size(); i++) {
             boolean unique = true;
             for (int j = 0; j < listeCote.size(); j++) {
                 if (i != j && listeCote.get(i).equals(listeCote.get(j))) {
                     unique = false;
                     break;
                 }
             }
             if (unique) {
                 listeCoteUnique.add(listeCote.get(i));
             }
         }
         return listeCoteUnique;
     }

     /**
      * Élimine les triangles qui contiennent les points du triangle de référence initial.
      * @param listeTriangle La liste des triangles à filtrer.
      * @return La liste des triangles filtrés.
      */
     public ArrayList<Triangle> epurationTriangle(ArrayList<Triangle> listeTriangle) {
         ArrayList<Triangle> nouvelleListeTriangle = new ArrayList<Triangle>();
         for (int i = 0; i < listeTriangle.size(); i++) {
             if (!(listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_1())
                     || listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_2())
                     || listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_3())
                     || listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_1())
                     || listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_2())
                     || listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_3())
                     || listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_1())
                     || listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_2())
                     || listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_3()))) {
                 nouvelleListeTriangle.add(listeTriangle.get(i));
             }
         }
         return nouvelleListeTriangle;
     }

     /**
      * Génère les lignes correspondant aux côtés des triangles.
      * @param listeTriangle La liste des triangles à afficher.
      * @return La liste des lignes représentant les côtés des triangles.
     */
     public ArrayList<Line> affichageTriangle(ArrayList<Triangle> listeTriangle) {
         ArrayList<Line> listeLigne = new ArrayList<Line>();
         for (int i = 0; i < listeTriangle.size(); i++) {
             listeLigne.add(new Line(listeTriangle.get(i).getPoint_1().getX(), listeTriangle.get(i).getPoint_1().getY(),
                     listeTriangle.get(i).getPoint_2().getX(), listeTriangle.get(i).getPoint_2().getY()));

             listeLigne.add(new Line(listeTriangle.get(i).getPoint_2().getX(), listeTriangle.get(i).getPoint_2().getY(),
                     listeTriangle.get(i).getPoint_3().getX(), listeTriangle.get(i).getPoint_3().getY()));

             listeLigne.add(new Line(listeTriangle.get(i).getPoint_3().getX(), listeTriangle.get(i).getPoint_3().getY(),
                     listeTriangle.get(i).getPoint_1().getX(), listeTriangle.get(i).getPoint_1().getY()));
         }
         return listeLigne;
     }

     /**
      * Génère une liste de triangles à partir des points de fin d'une transformation.
      * @param pointDebut La liste des points de départ.
      * @param pointFin   La liste des points de fin.
      * @return La liste des triangles générés à partir des points de fin.
      * @throws Exception Une exception en cas d'erreur de génération des triangles.
      */
     public ArrayList<Triangle> listeTriangleFin(ArrayList<Circle> pointDebut, ArrayList<Circle> pointFin) throws Exception {
         ArrayList<Triangle> listeTFin = new ArrayList<>();
         for (int j = 0; j < listeTriangle.size(); j++) {
             Point point_1 = null;
             Point point_2 = null;
             Point point_3 = null;
             for (int i = 0; i < pointDebut.size(); i++) {
                 if (pointDebut.get(i).getCenterX() == listeTriangle.get(j).getPoint_1().getX() && pointDebut.get(i).getCenterY() == listeTriangle.get(j).getPoint_1().getY()) {
                     point_1 = new Point(pointFin.get(i).getCenterX(), pointFin.get(i).getCenterY());
                 }
                 if (pointDebut.get(i).getCenterX() == listeTriangle.get(j).getPoint_2().getX() && pointDebut.get(i).getCenterY() == listeTriangle.get(j).getPoint_2().getY()) {
                     point_2 = new Point(pointFin.get(i).getCenterX(), pointFin.get(i).getCenterY());
                 }
                 if (pointDebut.get(i).getCenterX() == listeTriangle.get(j).getPoint_3().getX() && pointDebut.get(i).getCenterY() == listeTriangle.get(j).getPoint_3().getY()) {
                     point_3 = new Point(pointFin.get(i).getCenterX(), pointFin.get(i).getCenterY());
                 }
             }
             listeTFin.add(j, new Triangle(point_1, point_2, point_3));
         }
         return listeTFin;
     }

     /**
      * Convertit les cercles représentant les points en une liste de points.
      * @return La liste des points représentés par les cercles.
      */
     public ArrayList<Point> convertionPoint() {
         ArrayList<Point> listeP = new ArrayList<>();
         for (int i = 0; i < this.pointGraph1.size(); i++) {
             listeP.add(new Point(this.pointGraph1.get(i).getCenterX(), this.pointGraph1.get(i).getCenterY()));
         }
         return listeP;
     }
 }
