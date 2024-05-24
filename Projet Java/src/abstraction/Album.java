package abstraction;

import java.io.File;
import java.util.Observable;
import javafx.scene.image.Image;
import presentation.Fichier;

/**
 * Classe faisant la liaison entre les fichiers et les images
 * 
 * @auteur Reignier Arnaud
 * @auteur Vigneron Bastien
 * @auteur Finana Tom
 * @auteur Fontaine Nicolas
 * @auteur Charrier Simon
 *
 */
@SuppressWarnings("deprecation")
public class Album extends Observable {

    @SuppressWarnings("removal")
    public static final Integer IMAGE_DEPART = new Integer(0); // Constante pour l'image de départ
    @SuppressWarnings("removal")
    public static final Integer IMAGE_FIN = new Integer(1); // Constante pour l'image de fin
    
    private Image imageDepart; // Image de départ
    private Image imageFin; // Image de fin
    private Fichier fichier; // Fichier image à ajouter

    /**
     * Constructeur de la classe Album
     * 
     * @param fichier fichier à ajouter
     */
    public Album(Fichier fichier) {
        // À la création de l'album, on importe une image par défaut (le carré gris) et on initialise les 2 images
        File fichierParDefaut = new File("img/default.png");
        this.imageDepart = new Image(fichierParDefaut.toURI().toString());
        this.fichier = fichier;
        this.fichier.setF(fichierParDefaut);
        this.imageFin = new Image(fichierParDefaut.toURI().toString());
    }

    /**
     * Définit l'image de départ
     * 
     * @param cheminComplet Chemin vers l'image à afficher
     */
    public void setImageDepart(String cheminComplet) {
        this.imageDepart = new Image(cheminComplet);
        this.setChanged();
        this.notifyObservers(IMAGE_DEPART);
    }
    
    /**
     * Définit l'image de fin
     * 
     * @param cheminComplet Chemin vers l'image à afficher
     */
    public void setImageFin(String cheminComplet) {
        this.imageFin = new Image(cheminComplet);
        this.setChanged();
        this.notifyObservers(IMAGE_FIN);
    }
    
    /**
     * Retourne l'image de départ
     * 
     * @return L'image de départ
     */
    public Image getImageDepart() {
        return this.imageDepart;
    }
    
    /**
     * Retourne l'image de fin
     * 
     * @return L'image de fin
     */
    public Image getImageFin() {
        return this.imageFin;
    }
}