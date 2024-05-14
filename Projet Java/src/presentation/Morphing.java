package presentation;



// import java.io.File;

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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
// import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Morphing extends Application{
	
	private Album alb;
	private ImageView imageDepart;
	private ImageView imageFin;
	private Button boutonGauche;
	private Button boutonDroite;
	private Button boutonPoly;
	private Button boutonFA;
	private Button boutonVisage;
	
	
	public StackPane creerImageDepart(Image p) {
		//On créer l'image view
        imageDepart = new ImageView();
        imageDepart.setImage(p);
        // on peut changer la taille de l'image grâce au Rectangle 2D
        Rectangle2D viewportRect = new Rectangle2D(10, 10, 600, 450);
        imageDepart.setViewport(viewportRect);
        
        // Le stack pane permet de superposer le canva et l'image
        StackPane stack1 = new StackPane();
        Canvas canva1 = new Canvas(p.getWidth(), p.getHeight());
        canva1.setOnMouseClicked(e -> creationPoint(e, canva1));
        stack1.getChildren().addAll(imageDepart,canva1);
        
        return stack1;
	}
	
	public ImageView creerImageFin(Image p) {
		
       imageFin = new ImageView();
       imageFin.setImage(p);

       Rectangle2D viewportRect = new Rectangle2D(10, 10, 600, 450);
       imageFin.setViewport(viewportRect);
       
       return imageFin;
	}
	
	public Button creerBoutonGauche() {
		boutonGauche = new Button("Upload image");
		return boutonGauche;
	}
	
	public Button creerBoutonDroite() {
		boutonDroite = new Button("Upload image");
		return boutonDroite;
	}
	
	public void creationPoint(javafx.scene.input.MouseEvent e, Canvas canva) {
		// On récupère les coordonnées de la souris
		double x = e.getX();
		double y = e.getY();
		
		// On récupère les coordonnées relatives à l'images
		double xImg = canva.getLayoutX();
		double yImg = canva.getLayoutY();  
		
		// On dessine le point grâce au contexte graphique
		GraphicsContext gc = canva.getGraphicsContext2D();
		gc.setFill(Color.PLUM);
		gc.fillOval(x + xImg, y + yImg, 6, 6);
		
		// On créer le point
		//TODO
	}
	@SuppressWarnings("deprecation")
	@Override
    public void start(Stage primaryStage) throws Exception {
		
        /* donner un nom à la fenêtre */
        primaryStage.setTitle("Morphing d'image");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        
        /* création d'une fenêtre */
        HBox root = new HBox();
        
        alb = new Album();
        
        VBox vBoxGauche = new VBox();
        vBoxGauche.getChildren().add(creerImageDepart(alb.getImageDepart()));
        vBoxGauche.getChildren().add(creerBoutonGauche());

        ControleImageDepart cid = new ControleImageDepart(alb, imageDepart);
        alb.addObserver(cid);
        
        ControleBoutonGauche cbg = new ControleBoutonGauche(alb);
        boutonGauche.setOnAction(cbg);
        alb.addObserver(cbg);
        
        
        VBox vBoxDroite = new VBox();
        vBoxDroite.getChildren().add(creerImageFin(alb.getImageFin()));
        vBoxDroite.getChildren().add(creerBoutonDroite());
        
        ControleImageFin cif = new ControleImageFin(alb, imageFin);
        alb.addObserver(cif);
        
        ControleBoutonDroite cbd = new ControleBoutonDroite(alb);
        boutonDroite.setOnAction(cbd);
        alb.addObserver(cbd);
      
        root.getChildren().add(vBoxGauche);
        root.getChildren().add(vBoxDroite);
        
        /*Création du reste de la fenêtre*/
        /*Création des boutons en haut de la fenêtre*/
        boutonPoly = new Button("Polygone");
        boutonFA = new Button("Formes Arrondies");
        boutonVisage = new Button("Visages");
        HBox top = new HBox();
        // On fait en sorte que les boutons ne soient pas collés, et centré
        top.setSpacing(50);
        top.setPadding(new Insets(10,0,10,0));
        top.setAlignment(Pos.CENTER);
        // On ajoute les boutons
        top.getChildren().add(boutonPoly);
        top.getChildren().add(boutonFA);
        top.getChildren().add(boutonVisage);
        
        BorderPane visuPoly = new BorderPane();
        visuPoly.setCenter(root);
        visuPoly.setTop(top);
        
        
        
        /* création d'une scene et de son association avec */
        /* la fenêtre + taille */
        Scene scenePoly = new Scene(visuPoly);
        boutonPoly.setOnAction(e -> primaryStage.setScene(scenePoly));

        /* Ajouter la scene */
        primaryStage.setScene(scenePoly);

        /* rendre la fenêtre visible */
        primaryStage.show();

        }
	
    public static void main (String[] args) {
        launch (args);
    }
}
