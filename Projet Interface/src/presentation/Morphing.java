package presentation;



import java.io.File;

import abstraction.Album;
import controle.ControleBoutonDroite;
import controle.ControleBoutonGauche;
import controle.ControleImageDepart;
import controle.ControleImageFin;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Morphing extends Application{
	
	private Album alb;
	private ImageView imageDepart;
	private ImageView imageFin;
	private Button boutonGauche;
	private Button boutonDroite;
	
	public ImageView creerImageDepart(Image p) {
		
        imageDepart = new ImageView();
        imageDepart.setImage(p);

        Rectangle2D viewportRect = new Rectangle2D(10, 10, 600, 450);
        imageDepart.setViewport(viewportRect);
        
        return imageDepart;
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
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		
        /* donner un nom à la fenêtre */
        primaryStage.setTitle("Morphing d'image");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        
        /* création d'une fenêtre */
        HBox root = new HBox();
        
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(null);
        alb = new Album(new Image(file.toURI().toString()));
        
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
        
        /* création d'une scene et de son association avec */
        /* la fenêtre + taille */
        Scene scene = new Scene(root);
        

        /* Ajouter la scene */
        primaryStage.setScene(scene);

        /* rendre la fenêtre visible */
        primaryStage.show();

        }
	
    public static void main (String[] args) {
        launch (args);
    }
}
