package abstraction;

import java.io.File;
import java.util.Observable;

import ihm.Fichier;
import javafx.scene.image.Image;

/**
 * Classe faisant la liaison entre les fichiers et les images
 * 
 * @autor Reignier Arnaud
 * @autor Vigneron Bastien
 * @autor Finana Tom
 * @autor Fontaine Nicolas
 * @autor Charrier Simon
 */
@SuppressWarnings("deprecation")
public class Album extends Observable {

    // Constantes pour identifier les types d'images (départ et fin)
    @SuppressWarnings("removal")
    public static final Integer IMAGE_Depart = new Integer(0); // Identifiant pour l'image de départ
    @SuppressWarnings("removal")
    public static final Integer IMAGE_Fin = new Integer(1); // Identifiant pour l'image de fin

    // Attributs pour les images et les fichiers associés
    private Image imageDepart; // Image de départ
    private Image imageFin; // Image de fin
    private Fichier fD; // Fichier associé à l'image de départ
    private Fichier fG; // Fichier associé à l'image de fin

    /**
     * Constructeur de la classe Album
     * Initialise les images de départ et de fin avec une image par défaut
     * 
     * @param fG fichier associé à l'image de fin
     * @param fD fichier associé à l'image de départ
     */
    public Album(Fichier fG, Fichier fD) {
        // À la création de l'album, on importe une image par défaut (le carré gris)
        File file = new File("img/default.png");
        this.imageDepart = new Image(file.toURI().toString()); // Initialise l'image de départ
        this.fD = fD;
        this.fG = fG;
        this.fD.setF(file); // Associe le fichier fD à l'image par défaut
        this.fG.setF(file); // Associe le fichier fG à l'image par défaut
        this.imageFin = new Image(file.toURI().toString()); // Initialise l'image de fin
    }

    /**
     * Met à jour l'image de départ avec une nouvelle image
     * 
     * @param fullpathname Chemin complet vers la nouvelle image
     */
    public void setImageDepart(String fullpathname) {
        this.imageDepart = new Image(fullpathname); // Met à jour l'image de départ
        this.setChanged(); // Indique que l'état a changé
        this.notifyObservers(IMAGE_Depart); // Notifie les observateurs du changement
    }
    
    /**
     * Met à jour l'image de fin avec une nouvelle image
     * 
     * @param fullpathname Chemin complet vers la nouvelle image
     */
    public void setImageFin(String fullpathname) {
        this.imageFin = new Image(fullpathname); // Met à jour l'image de fin
        this.setChanged(); // Indique que l'état a changé
        this.notifyObservers(IMAGE_Fin); // Notifie les observateurs du changement
    }
    
    /**
     * Retourne l'image de départ
     * 
     * @return L'image de départ
     */
    public Image getImageDepart() {
        return this.imageDepart; // Retourne l'image de départ
    }
    
    /**
     * Retourne l'image de fin
     * 
     * @return L'image de fin
     */
    public Image getImageFin() {
        return this.imageFin; // Retourne l'image de fin
    }
}
