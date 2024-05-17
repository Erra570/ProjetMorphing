package abstraction;

import java.io.File;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import presentation.Fichier;

/**
 * Classe faisant la liaison entre les fichiers et les images
 * 
 * @author Reignier Arnaud
 * @author Vigneron Bastien
 * @author Finana Tom
 * @author Fontaine Nicolas
 * @author Charrier Simon
 *
 */
@SuppressWarnings("deprecation")
public class Album extends Observable {

	@SuppressWarnings("removal")
	public static final Integer IMAGE_Depart = new Integer(0); // PAC
	@SuppressWarnings("removal")
	public static final Integer IMAGE_Fin = new Integer(1); // PAC
	
	private Image imageDepart; // Image de départ
	private Image imageFin; // Image de fin
	private Fichier f; // Fichier image à ajouter

	/**
	 * Constructeur de la classe album
	 * 
	 * @param f fichier à ajouter
	 */
	public Album(Fichier f) {
		// à la création de l'album, on importe une image par défaut (le carrée gris) et on initialise les 2 images
		File file = new File("img/default.png");
		this.imageDepart = new Image(file.toURI().toString());
		this.f = f;
		f.setF(file);
		this.imageFin = new Image(file.toURI().toString());
	}

	/**
	 * Setteur de l'image de départ
	 * 
	 * @param fullpathname Chemin vers l'image à afficher
	 */
	public void setImageDepart(String fullpathname) {
		this.imageDepart = new Image(fullpathname);
		this.setChanged(); // PAC
		this.notifyObservers(IMAGE_Depart); // PAC
	}
	
	/**
	 * Setteur de l'image de fin
	 * 
	 * @param fullpathname Chemin vers l'image à afficher
	 */
	public void setImageFin(String fullpathname) {
		this.imageFin = new Image(fullpathname);
		this.setChanged(); // PAC
		this.notifyObservers(IMAGE_Fin); // PAC
	}
	
	/**
	 * Getteur de l'image de départ
	 * 
	 * @return L'image de départ
	 */
	public Image getImageDepart() {
		return(this.imageDepart);
	}
	
	/**
	 * Getteur de l'image de fin
	 * 
	 * @return L'image d'arrivée
	 */
	public Image getImageFin() {
		return(this.imageFin);
	}
}