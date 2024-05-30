package controle;

import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.scene.image.ImageView;

/**
 * Classe de contrôle pour mettre à jour l'image de fin dans l'interface utilisateur
 */
@SuppressWarnings("deprecation")
public class ControleImageFin implements Observer {
	
    private Album alb; // Référence à l'album
    private ImageView image; // Composant ImageView de l'interface utilisateur

    /**
     * Constructeur de la classe ControleImageFin
     * 
     * @param alb L'album à contrôler
     * @param image Le composant ImageView à mettre à jour
     */
    public ControleImageFin(Album alb, ImageView image) {
        this.alb = alb;
        this.image = image;
    }

    /**
     * Méthode appelée lorsque l'objet observé est mis à jour
     * 
     * @param o L'objet observable
     * @param arg Argument passé à la méthode notifyObservers, utilisé pour indiquer le type de changement
     */
    @Override
    public void update(Observable o, Object arg) {
        // Vérifie si l'argument passé correspond à l'identifiant de l'image de fin
        if (((Integer) arg).intValue() == Album.IMAGE_Fin) {
            // Met à jour l'image affichée dans l'ImageView
            image.setImage(alb.getImageFin());    
        }
    }
}
