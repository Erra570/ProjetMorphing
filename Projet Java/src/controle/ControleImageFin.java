package controle;

import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.scene.image.ImageView;

/**
 * Classe de contrôle pour la mise à jour de l'image de fin dans l'album
 * 
 * @author Reignier Arnaud
 * @author Vigneron Bastien
 * @author Finana Tom
 * @author Fontaine Nicolas
 * @author Charrier Simon
 */
@SuppressWarnings("deprecation")
public class ControleImageFin implements Observer{
	
	private Album alb; // Album utilisé
	private ImageView image; // Composant ImageView pour afficher l'image
	
	
    /**
     * Constructeur de la classe ControleImageDepart
     * 
     * @param alb Référence à l'album
     * @param image Composant ImageView pour afficher l'image
     */
	public ControleImageFin(Album alb, ImageView image) {
		this.alb = alb;
		this.image = image;
	}

    /**
     * Méthode de mise à jour de l'observateur
     * 
     * @param obs L'objet observable
     * @param arg Argument passé à l'observateur
     */
	@Override
	public void update(Observable obs, Object arg) {
		if(((Integer) arg).intValue() == 1)
		{
			image.setImage(alb.getImageFin());	
		}
	}
}
