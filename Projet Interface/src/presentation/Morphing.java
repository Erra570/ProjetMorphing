package presentation;



import java.io.File;

import abstraction.Album;
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
	
	public ImageView creerImageFin (Image p) {
		
       imageFin = new ImageView();
       imageFin.setImage(p);

       Rectangle2D viewportRect = new Rectangle2D(10, 10, 600, 450);
       imageFin.setViewport(viewportRect);
       
       return imageFin;
	}
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		
        /* donner un nom à la fenêtre */
        primaryStage.setTitle("Morphing d'image");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        
        HBox hBoxGauche = new HBox();
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(null);
        alb = new Album(new Image(file.toURI().toString()));
        /* création d'une fenêtre */
        VBox root = new VBox();
        hBoxGauche.getChildren().add(creerImageDepart(alb.getImageDepart()));
        
        HBox hBoxDroite = new HBox();
        hBoxGauche.getChildren().add(creerImageFin(alb.getImageFin()));
        
        ControleImageDepart cid = new ControleImageDepart(alb, imageDepart);
        alb.addObserver(cid);
        
        ControleImageFin cif = new ControleImageFin(alb, imageFin);
        alb.addObserver(cif);
      
        root.getChildren().add(hBoxGauche);
        root.getChildren().add(hBoxDroite);
        
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
