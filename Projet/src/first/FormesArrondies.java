package first;

import java.util.ArrayList;
import java.util.List;

import abstraction.Album;
import controle.ControleBoutonDroite;
import controle.ControleBoutonGauche;
import controle.ControleImageDepart;
import controle.ControleImageFin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.GestureEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import presentation.Fichier;

/**
 * Classe permettant de créer des courbes pour les formes arrondies
 *
 */
public class FormesArrondies extends Application {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private List<QCurve> curves1 = new ArrayList<>(); // liste de courbes
	private List<QCurve> curves2 = new ArrayList<>(); // liste de courbes pour l'image de fin
	private List<Circle> points1 = new ArrayList<>(); // liste de points
	private List<Circle> points2 = new ArrayList<>(); // liste de points pour l'image de fin
	private Pane gestPoints1;
	private Pane gestPoints2;
	private boolean closeState = false; // etat de fermeture du périmètre
	private Scene scene;
	private Album alb;
	private ImageView imageDepart;
	private ImageView imageFin;
	private Button boutonGauche;
	private Button boutonDroite;
	private Button boutonPoly;
	private Button boutonFA;
	private Button boutonVisage;
	private Fichier f;
	private BorderPane root;
	private HBox pCentre;
	private VBox right;

	@Override
	public void start(Stage primaryStage) {

		/* création du plan central, contenant les images */
		HBox pCentre = new HBox();
		pCentre.setSpacing(10);
		f = new Fichier();
		alb = new Album(f);

		// Image gauche avec bouton pour changer d'image
		VBox vBoxGauche = new VBox();
		vBoxGauche.getChildren().add(creerImageDepart(alb.getImageDepart()));
		vBoxGauche.getChildren().add(creerBoutonGauche());
		ControleImageDepart cid = new ControleImageDepart(alb, imageDepart);
		alb.addObserver(cid);
		ControleBoutonGauche cbg = new ControleBoutonGauche(alb, f);
		boutonGauche.setOnAction(cbg);
		alb.addObserver(cbg);

		// Image droite avec bouton pour changer d'image
		VBox vBoxDroite = new VBox();
		vBoxDroite.getChildren().add(creerImageFin(alb.getImageFin()));
		vBoxDroite.getChildren().add(creerBoutonDroite());
		ControleImageFin cif = new ControleImageFin(alb, imageFin);
		alb.addObserver(cif);
		ControleBoutonDroite cbd = new ControleBoutonDroite(alb, f);
		boutonDroite.setOnAction(cbd);
		alb.addObserver(cbd);

		pCentre.getChildren().addAll(vBoxGauche, vBoxDroite);

		right = new VBox();
		Button clo = new Button("Fermer Forme");
		clo.setOnAction(event -> close()); // Définir le gestionnaire d'événements
		right.getChildren().add(clo);

		Button del = new Button("Annuler");
		del.setOnAction(event -> delete()); // Définir le gestionnaire d'événements
		right.getChildren().add(del);

		right.setSpacing(50);
		right.setAlignment(Pos.CENTER);

		root = new BorderPane();
		root.setCenter(pCentre);
		root.setRight(right);

		scene = new Scene(root);

		MouseClickHandler clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState,
				gestPoints1, gestPoints2);
		gestPoints1.setOnMouseClicked(clickHandler);

		MouseMoveHandler moveHandler1 = new MouseMoveHandler(curves1, points1, closeState, gestPoints1, 1);
		MouseMoveHandler moveHandler2 = new MouseMoveHandler(curves2, points2, closeState, gestPoints2, 2);

		gestPoints1.setOnMouseMoved(moveHandler1);
		gestPoints2.setOnMouseMoved(moveHandler2);

		/* donner un nom et une taille à la fenêtre */
		primaryStage.setScene(scene);
		primaryStage.setTitle("Morphing d'image");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Création de l'image gauche sur l'interface
	 * 
	 * @param p image à afficher
	 * @return le plan contenant l'image dans une imageView et le gestionnaire de
	 *         points
	 */
	public StackPane creerImageDepart(Image p) {
		imageDepart = new ImageView();
		imageDepart.setImage(p);
		Rectangle2D viewportRect = new Rectangle2D(0, 0, 500, 500);
		imageDepart.setViewport(viewportRect);
		gestPoints1 = new Pane();
		gestPoints1.prefHeight(p.getHeight());
		gestPoints1.prefWidth(p.getWidth());
		StackPane stack1 = new StackPane();
		stack1.getChildren().addAll(imageDepart, gestPoints1);
		return stack1;
	}

	/**
	 * Création de l'image de droite (image de fin)
	 * 
	 * @param p image à afficher
	 * @return le plan contenant l'image dans une imageView et le gestionnaire de
	 *         points
	 */
	public StackPane creerImageFin(Image p) {
		imageFin = new ImageView();
		imageFin.setImage(p);
		Rectangle2D viewportRect = new Rectangle2D(0, 0, 500, 500);
		imageFin.setViewport(viewportRect);
		gestPoints2 = new Pane();
		gestPoints2.prefHeight(p.getHeight());
		gestPoints2.prefWidth(p.getWidth());
		StackPane stack2 = new StackPane();
		stack2.getChildren().addAll(imageFin, gestPoints2);
		return stack2;
	}

	/**
	 * Création du bouton pour modifier l'image de début
	 * 
	 * @return le bouton
	 */
	public Button creerBoutonGauche() {
		boutonGauche = new Button("Changer d'image");
		return boutonGauche;
	}

	/**
	 * Création du bouton pour modifier l'image de fin
	 * 
	 * @return le bouton
	 */
	public Button creerBoutonDroite() {
		boutonDroite = new Button("Changer d'image");
		return boutonDroite;
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
	private void delete() {
		if (closeState) {
			curves1.get(curves1.size() - 1).delete(gestPoints1);
			curves1.remove(curves1.size() - 1);
			curves2.get(curves2.size() - 1).delete(gestPoints2);
			curves2.remove(curves2.size() - 1);
			gestPoints1.getChildren().remove(points1.remove(points1.size() - 1));
			gestPoints2.getChildren().remove(points2.remove(points2.size() - 1));
			closeState = false;

			MouseClickHandler clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState,
					gestPoints1, gestPoints2);
			gestPoints1.setOnMouseClicked(clickHandler);
			MouseMoveHandler moveHandler1 = new MouseMoveHandler(curves1, points1, closeState, gestPoints1, 1);
			MouseMoveHandler moveHandler2 = new MouseMoveHandler(curves2, points2, closeState, gestPoints2, 2);
			gestPoints1.setOnMouseMoved(moveHandler1);
			gestPoints2.setOnMouseMoved(moveHandler2);

		} else {
			if (!curves1.isEmpty()) {
				curves1.get(curves1.size() - 1).delete(gestPoints1);
				curves1.remove(curves1.size() - 1);
				curves2.get(curves2.size() - 1).delete(gestPoints2);
				curves2.remove(curves2.size() - 1);
			}
			if (!points1.isEmpty()) {
				gestPoints1.getChildren().remove(points1.remove(points1.size() - 1));
				gestPoints2.getChildren().remove(points2.remove(points2.size() - 1));
			}
			if (!points1.isEmpty()) {
				gestPoints1.getChildren().remove(points1.remove(points1.size() - 1));
				gestPoints2.getChildren().remove(points2.remove(points2.size() - 1));
			}
		}
		System.out.println("" + points1.size());
		System.out.println("" + curves1.size());
	}

	/**
	 * Permet de fermé le graphe en reliant le dernier point au premier
	 */
	private void close() {
		Circle pointControl1 = midle(points1.get(points1.size() - 2), points1.get(0));
		pointControl1.setFill(Color.GREEN);
		points1.add(pointControl1);
		gestPoints1.getChildren().add(pointControl1);

		Circle pointControl2 = midle(points2.get(points2.size() - 2), points2.get(0));
		pointControl2.setFill(Color.GREEN);
		points2.add(pointControl2);
		gestPoints2.getChildren().add(pointControl2);

		QCurve curve1 = new QCurve(points1.get(points1.size() - 3).getCenterX(),
				points1.get(points1.size() - 3).getCenterY(), points1.get(points1.size() - 1).getCenterX(),
				points1.get(points1.size() - 1).getCenterY(), points1.get(0).getCenterX(), points1.get(0).getCenterY());
		curves1.add(curve1);
		curve1.drawCurve(gestPoints1);

		QCurve curve2 = new QCurve(points2.get(points2.size() - 3).getCenterX(),
				points2.get(points2.size() - 3).getCenterY(), points2.get(points2.size() - 1).getCenterX(),
				points2.get(points2.size() - 1).getCenterY(), points2.get(0).getCenterX(), points2.get(0).getCenterY());
		curves2.add(curve2);
		curve2.drawCurve(gestPoints2);
		closeState = true;

		MouseClickHandler clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState,
				gestPoints1, gestPoints2);
		gestPoints1.setOnMouseClicked(clickHandler);

		MouseMoveHandler moveHandler1 = new MouseMoveHandler(curves1, points1, closeState, gestPoints1, 1);
		MouseMoveHandler moveHandler2 = new MouseMoveHandler(curves2, points2, closeState, gestPoints2, 2);

		gestPoints1.setOnMouseMoved(moveHandler1);
		gestPoints2.setOnMouseMoved(moveHandler2);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
