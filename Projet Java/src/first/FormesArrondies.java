package first;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import abstraction.Album;
import controle.ControleBoutonDroite;
import controle.ControleBoutonGauche;
import controle.ControleImageDepart;
import controle.ControleImageFin;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import morphingFonction.MorphingImg;
import presentation.Fichier;
import triangle.Delaunay;
import triangle.MouseClickHandlerDelaunay;
import triangle.MouseMoveHandlerDelaunay;
import triangle.MouseDragHandlerDelaunay;

/**
 * Classe permettant de créer des courbes pour les formes arrondies
 *
 */
public class FormesArrondies extends Application {

	// Premier plan
	private List<QCurve> curves1 = new ArrayList<>(); // liste de courbes
	private List<QCurve> curves2 = new ArrayList<>(); // liste de courbes pour l'image de fin
	private List<Circle> points1 = new ArrayList<>(); // liste de points
	private List<Circle> points2 = new ArrayList<>(); // liste de points pour l'image de fin
	private ArrayList<Circle> pointGraph1 = new ArrayList<>(); // liste de points
	private ArrayList<Circle> pointGraph2 = new ArrayList<>(); // liste de points pour l'image de fin
	private Pane gestPoints1;
	private Pane gestPoints2;
	private boolean closeState = false; // etat de fermeture du périmètre
	private Scene scene;
	private Scene showResult;
	private Album alb;
	private ImageView imageDepart;
	private ImageView imageFin;
	private Button boutonGauche;
	private Button boutonDroite;
	private TextField texte;
	private Fichier f;
	private BorderPane root;
	private VBox loading;
	private HBox pCentre;
	private VBox right;
	private ColorPicker coulCurv;
	private Button clo;
	private Button del;
	private Button startMorph;
	private QCurve curve1;
	private QCurve curve2;
	private ControleBoutonDroite cbd;
	private ControleBoutonGauche cbg;
	private ProgressIndicator pBar;
	private Task<Scene> morphingTask;
	private TextField nbImagesPC;
	private ColorPicker pickSom = new ColorPicker(Color.RED);
	private MouseClickHandler clickHandler;
	private MouseClickHandlerDelaunay clickHandlerDelaunay;
	private ColorPicker pickCont;
	private boolean modeState=false;

	private Delaunay delaunay1;
	private Delaunay delaunay2;

	@Override
	public void start(Stage primaryStage) {
		interfaceFormes(primaryStage);
	}

	@SuppressWarnings("deprecation")
	public void interfaceFormes(Stage primaryStage) {
		modeState=false;
		/* création du plan central, contenant les images */
		pCentre = new HBox();
		pCentre.setSpacing(10);
		pCentre.setAlignment(Pos.CENTER);
		f = new Fichier();
		alb = new Album(f);

		// Partie haute : changement de mode
		Button chgmtCote = new Button("Passer au Mode Visage");
		chgmtCote.setOnAction(e -> goToVisage(primaryStage));
		StackPane haut = new StackPane();
		haut.getChildren().add(chgmtCote);

		// Barre de chargement
		loading = new VBox();
		pBar = new ProgressIndicator();
		texte = new TextField("Morphing en cours...");
		loading.getChildren().add(texte);
		loading.getChildren().add(pBar);

		Scene loadScene = new Scene(loading);

		// Image gauche avec bouton pour changer d'image
		VBox vBoxGauche = new VBox();
		vBoxGauche.setAlignment(Pos.CENTER);
		vBoxGauche.getChildren().add(creerImageDepart(alb.getImageDepart()));
		vBoxGauche.getChildren().add(creerBoutonGauche());
		ControleImageDepart cid = new ControleImageDepart(alb, imageDepart);
		alb.addObserver(cid);
		cbg = new ControleBoutonGauche(alb, f);
		boutonGauche.setOnAction(cbg);
		boutonGauche.setPrefWidth(450);
		alb.addObserver(cbg);

		// Image droite avec bouton pour changer d'image
		VBox vBoxDroite = new VBox();
		vBoxDroite.setAlignment(Pos.CENTER);
		vBoxDroite.getChildren().add(creerImageFin(alb.getImageFin()));
		vBoxDroite.getChildren().add(creerBoutonDroite());
		ControleImageFin cif = new ControleImageFin(alb, imageFin);
		alb.addObserver(cif);
		cbd = new ControleBoutonDroite(alb, f);
		boutonDroite.setOnAction(cbd);
		boutonDroite.setPrefWidth(450);
		alb.addObserver(cbd);

		this.pCentre.getChildren().addAll(vBoxGauche, vBoxDroite);

		// Partie droite (boutons et couleurs)
		right = new VBox();
		right.setAlignment(Pos.CENTER);
		clo = new Button("Fermer Forme");
		clo.setOnAction(event -> close()); // Définir le gestionnaire d'événements
		clo.setDisable(true);

		del = new Button("Supprimer Dernier Point");
		del.setDisable(true);
		del.setOnAction(event -> delete()); // Définir le gestionnaire d'événements

		startMorph = new Button("Commencer le morphing");
		startMorph.setDisable(true);
		startMorph.setOnAction(event -> {
			if (Double.parseDouble(nbImagesPC.getText()) < 5) {
				Alert basseVitesse = new Alert(AlertType.ERROR);
				basseVitesse.initModality(Modality.APPLICATION_MODAL);
				basseVitesse.setHeaderText("Erreur - Vitesse trop basse !");
				basseVitesse.setContentText(
						"Pour eviter un plantage dû au nombre d'images calculées, veuillez augmenter la vitesse au dessus de 5%");
				basseVitesse.showAndWait();
			} else {
				try {
					primaryStage.setScene(loadScene);
					morphingTask = new Task<>() {
						@Override
						protected Scene call() throws Exception {
							try {
								startMorphing(primaryStage);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return scene;
						}
					};
					morphingTask.setOnSucceeded(event2 -> primaryStage.setScene(showResult)); // primaryStage.setScene(morphingTask.getValue())
					new Thread(morphingTask).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Label labCoulCurv = new Label("Couleur de la courbe");
		coulCurv = new ColorPicker(Color.BLUE);
		coulCurv.setOnAction(event -> changeColor(coulCurv.getValue()));

		Label labpickSom = new Label("Couleur des sommets");
		pickSom.setOnAction(event -> colorSomFA(pickSom.getValue()));

		Label labpickCont = new Label("Couleur des points de contrôles");
		pickCont = new ColorPicker(Color.GREEN);
		pickCont.setOnAction(event -> colorContFA(pickCont.getValue()));

		Label labnbImagesPC = new Label("Vitesse (%)");
		nbImagesPC = new TextField("100");
		nbImagesPC.setOnKeyPressed(e -> verifNbImage(nbImagesPC));

		clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState, gestPoints1, gestPoints2,
				clo, del, coulCurv.getValue(), pickSom.getValue(), pickCont.getValue());
		gestPoints1.setOnMouseClicked(clickHandler);

		MouseMoveHandler moveHandler1 = new MouseMoveHandler(curves1, points1, closeState, gestPoints1, 1);
		MouseMoveHandler moveHandler2 = new MouseMoveHandler(curves2, points2, closeState, gestPoints2, 2);

		gestPoints1.setOnMouseMoved(moveHandler1);
		gestPoints2.setOnMouseMoved(moveHandler2);

		right.getChildren().addAll(clo, del, labCoulCurv, coulCurv, labpickSom, pickSom, labpickCont, pickCont,
				labnbImagesPC, nbImagesPC, startMorph);

		root = new BorderPane();
		root.setCenter(this.pCentre);
		root.setRight(right);
		root.setTop(haut);
		scene = new Scene(root);

		// Ajouts de la feuille css
		scene.getStylesheets().add("file:css/Style.css");
		clo.getStyleClass().add("bouton");
		clo.getStyleClass().add("boutonDroit");
		del.getStyleClass().add("bouton");
		del.getStyleClass().add("boutonDroit");
		boutonDroite.getStyleClass().add("bouton");
		boutonGauche.getStyleClass().add("bouton");
		coulCurv.getStyleClass().add("bouton");
		coulCurv.getStyleClass().add("boutonDroit");
		startMorph.getStyleClass().add("bouton");
		startMorph.getStyleClass().add("boutonDroit");
		right.getStyleClass().add("panDroit");
		nbImagesPC.getStyleClass().add("boutonDroit");
		pickSom.getStyleClass().add("boutonDroit");
		pickCont.getStyleClass().add("boutonDroit");

		/* donner un nom et une taille à la fenêtre */
		primaryStage.setScene(scene);
		primaryStage.setTitle("Morphing d'image");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@SuppressWarnings("deprecation")
	public void interfaceVisage(Stage primaryStage) {
		modeState = true;
		/* création du plan central, contenant les images */
		pCentre = new HBox();
		pCentre.setSpacing(10);
		pCentre.setAlignment(Pos.CENTER);
		f = new Fichier();
		alb = new Album(f);

		// Partie haute : changement de mode
		Button chgmtCote = new Button("Passer au Mode Formes");
		chgmtCote.setOnAction(e -> goToForme(primaryStage));
		StackPane haut = new StackPane();
		haut.getChildren().add(chgmtCote);

		// Barre de chargement
		loading = new VBox();
		pBar = new ProgressIndicator();
		texte = new TextField("Morphing en cours...");
		loading.getChildren().add(texte);
		loading.getChildren().add(pBar);

		// Image gauche avec bouton pour changer d'image
		VBox vBoxGauche = new VBox();
		vBoxGauche.setAlignment(Pos.CENTER);
		vBoxGauche.getChildren().add(creerImageDepart(alb.getImageDepart()));
		vBoxGauche.getChildren().add(creerBoutonGauche());
		ControleImageDepart cid = new ControleImageDepart(alb, imageDepart);
		alb.addObserver(cid);
		cbg = new ControleBoutonGauche(alb, f);
		boutonGauche.setOnAction(cbg);
		boutonGauche.setPrefWidth(450);
		alb.addObserver(cbg);

		// Image droite avec bouton pour changer d'image
		VBox vBoxDroite = new VBox();
		vBoxDroite.setAlignment(Pos.CENTER);
		vBoxDroite.getChildren().add(creerImageFin(alb.getImageFin()));
		vBoxDroite.getChildren().add(creerBoutonDroite());
		ControleImageFin cif = new ControleImageFin(alb, imageFin);
		alb.addObserver(cif);
		cbd = new ControleBoutonDroite(alb, f);
		boutonDroite.setOnAction(cbd);
		boutonDroite.setPrefWidth(450);
		alb.addObserver(cbd);

		this.pCentre.getChildren().addAll(vBoxGauche, vBoxDroite);

		// Partie droite (boutons et couleurs)
		right = new VBox();
		right.setAlignment(Pos.CENTER);

		del = new Button("Supprimer Dernier Point");
		del.setDisable(true);
		del.setOnAction(event -> delete()); // Définir le gestionnaire d'événements //TODO delete

		startMorph = new Button("Trianguler");
		startMorph.setOnAction(event -> {
			delaunay1.initTriang(gestPoints1);
			startMorph.setDisable(true);
		});

		clickHandlerDelaunay = new MouseClickHandlerDelaunay(delaunay1.getPGraphe(), gestPoints1,
				delaunay2.getPGraphe(), gestPoints2, pickSom.getValue(), modeState);
		gestPoints1.setOnMouseClicked(clickHandlerDelaunay);

		Label labpickSom = new Label("Couleur des points");
		// TODO verif couleur des points
		pickSom.setOnAction(event -> {
			clickHandlerDelaunay.setCouleur(pickSom.getValue());
		});

		Label labnbImagesPC = new Label("Vitesse (%)");
		// TODO vitesse fonctionnelle ?
		nbImagesPC = new TextField("100");
		nbImagesPC.setOnKeyPressed(e -> verifNbImage(nbImagesPC));

		// TODO new move handler ?
		MouseMoveHandlerDelaunay moveHandlerDelaunay1 = new MouseMoveHandlerDelaunay(delaunay1.getPGraphe(),
				gestPoints1, 1);
		MouseMoveHandlerDelaunay moveHandlerDelaunay2 = new MouseMoveHandlerDelaunay(delaunay2.getPGraphe(),
				gestPoints2, 2);

		gestPoints1.setOnMouseMoved(moveHandlerDelaunay1);
		gestPoints2.setOnMouseMoved(moveHandlerDelaunay2);

		right.getChildren().addAll(del, labpickSom, pickSom, labnbImagesPC, nbImagesPC, startMorph);

		root = new BorderPane();
		root.setCenter(this.pCentre);
		root.setRight(right);
		root.setTop(haut);
		scene = new Scene(root);

		// Ajouts de la feuille css
		scene.getStylesheets().add("file:css/Style.css");
		del.getStyleClass().add("bouton");
		del.getStyleClass().add("boutonDroit");
		boutonDroite.getStyleClass().add("bouton");
		boutonGauche.getStyleClass().add("bouton");
		startMorph.getStyleClass().add("bouton");
		startMorph.getStyleClass().add("boutonDroit");
		right.getStyleClass().add("panDroit");
		nbImagesPC.getStyleClass().add("boutonDroit");
		pickSom.getStyleClass().add("boutonDroit");

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
		gestPoints1.prefHeight(500);
		gestPoints1.prefWidth(500);
		delaunay1 = new Delaunay(gestPoints1, gestPoints2, pointGraph1, pointGraph2, pickSom.getValue(),modeState);
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
		gestPoints2.prefHeight(500);
		gestPoints2.prefWidth(500);
		delaunay2 = new Delaunay(gestPoints2, gestPoints1, pointGraph2, pointGraph1, pickSom.getValue(),modeState);
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
		pointControl.getStyleClass().add("pts"); // Ajout pour le curseur
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
			clo.setDisable(false);
			startMorph.setDisable(true);

			clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState, gestPoints1,
					gestPoints2, clo, del, coulCurv.getValue(), pickSom.getValue(), pickCont.getValue());
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
			if (points1.size() < 2) {
				clo.setDisable(true);
			}
			if (points1.size() < 1) {
				del.setDisable(true);
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
		pointControl1.setFill(pickCont.getValue());
		points1.add(pointControl1);
		gestPoints1.getChildren().add(pointControl1);

		Circle pointControl2 = midle(points2.get(points2.size() - 2), points2.get(0));
		pointControl2.setFill(pickCont.getValue());
		points2.add(pointControl2);
		gestPoints2.getChildren().add(pointControl2);

		curve1 = new QCurve(points1.get(points1.size() - 3).getCenterX(), points1.get(points1.size() - 3).getCenterY(),
				points1.get(points1.size() - 1).getCenterX(), points1.get(points1.size() - 1).getCenterY(),
				points1.get(0).getCenterX(), points1.get(0).getCenterY(), coulCurv.getValue());
		curves1.add(curve1);
		curve1.drawCurve(gestPoints1);

		curve2 = new QCurve(points2.get(points2.size() - 3).getCenterX(), points2.get(points2.size() - 3).getCenterY(),
				points2.get(points2.size() - 1).getCenterX(), points2.get(points2.size() - 1).getCenterY(),
				points2.get(0).getCenterX(), points2.get(0).getCenterY(), coulCurv.getValue());
		curves2.add(curve2);
		curve2.drawCurve(gestPoints2);
		closeState = true;
		clo.setDisable(true);
		startMorph.setDisable(false);

		clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState, gestPoints1, gestPoints2,
				clo, del, coulCurv.getValue(), pickSom.getValue(), pickCont.getValue());
		gestPoints1.setOnMouseClicked(clickHandler);

		MouseMoveHandler moveHandler1 = new MouseMoveHandler(curves1, points1, closeState, gestPoints1, 1);
		MouseMoveHandler moveHandler2 = new MouseMoveHandler(curves2, points2, closeState, gestPoints2, 2);

		gestPoints1.setOnMouseMoved(moveHandler1);
		gestPoints2.setOnMouseMoved(moveHandler2);

	}

	private void changeColor(Color couleur) {
		for (int i = 0; i < curves1.size(); i++) {
			curves1.get(i).colorChange(couleur, gestPoints1);
			curves2.get(i).colorChange(couleur, gestPoints2);
		}
		clickHandler = new MouseClickHandler(curves1, curves2, points1, points2, closeState, gestPoints1, gestPoints2,
				clo, del, coulCurv.getValue(), pickSom.getValue(), pickCont.getValue());
		gestPoints1.setOnMouseClicked(clickHandler);
	}

	private void colorSomFA(Color coulSom) {
		clickHandler.changeColorSommets(coulSom);
	}

	private void colorContFA(Color coulCont) {
		clickHandler.changeColorCont(coulCont);
	}

	private void startMorphing(Stage primaryStage) throws Exception {

		List<QCurve> tabD = new ArrayList<>();
		List<QCurve> tabF = new ArrayList<>();
		for (int i = 0; i < curves1.size(); i++) {
			tabD.add(i, curves1.get(i));
			tabF.add(i, curves2.get(i));
		}

		MorphingImg m = new MorphingImg(cbg.getF(), tabD);

		// m.imgSuivanteFormeArrondie(tabSuivant);
		// m.creerImage();
		Double nbImages = (100 * 60) / (Double.parseDouble(nbImagesPC.getText())); // Produit en croix modifié pour que
																					// l'augmentation du % de vitesse
																					// donne une baisse d'images
		System.out.println(nbImages);
		m.creerGif(tabD, tabF, nbImages);

		/* Création de la scène de résultats */
		File fRes = new File("img/testGif.gif");
		ImageView imgResult = new ImageView(fRes.toURI().toString());
		Button retour = new Button("Retour");
		retour.setOnAction(e -> primaryStage.setScene(scene));
		VBox resultBox = new VBox();
		resultBox.getChildren().addAll(imgResult, retour);
		showResult = new Scene(resultBox);

	}

	/**
	 * Fonction permettant au TextField "nbImages" de n'accepter que des chiffres
	 * 
	 * @param field TextField à traiter
	 */
	public static void verifNbImage(TextField field) {
		// On ajoute un TextFormatter au TextField, outil pratique pour ce genre de cas.
		// Ici, <Integer> force le text à changer.
		field.setTextFormatter(new TextFormatter<Integer>(change -> {
			String newText = change.getControlNewText();
			// On vérifie via matches("\\d*") (\\d --> caractères entre 0 et 9, * -->
			// n'importe quel nombre de caractères respectant la condition)
			if (newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
	}

	public void goToVisage(Stage primaryStage) {
		// Nettoyage des listes de points pour éviter tous problèmes (on repart de 0)
		clickHandler.getcurves1().clear();
		clickHandler.getcurves2().clear();
		clickHandler.getPoints1().clear();
		clickHandler.getPoints2().clear();

		interfaceVisage(primaryStage);
	}

	public void goToForme(Stage primaryStage) {
		// Nettoyage des listes de points pour éviter tous problèmes (on repart de 0)
		clickHandler.getcurves1().clear();
		clickHandler.getcurves2().clear();
		clickHandler.getPoints1().clear();
		clickHandler.getPoints2().clear();

		interfaceFormes(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
