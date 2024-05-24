package controle;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import presentation.Fichier;

/**
 * Classe de contrôle pour le bouton de gauche permettant de sélectionner une image de départ
 * et de la mettre à jour dans l'album.
 * 
 * @author Reignier Arnaud
 * @author Vigneron Bastien
 * @author Finana Tom
 * @author Fontaine Nicolas
 * @author Charrier Simon
 */
@SuppressWarnings("deprecation")
public class ControleBoutonGauche implements Observer, EventHandler<ActionEvent>{
	
	private Album alb;
	public Fichier fichier;
	
	
    /**
     * Constructeur de la classe ControleBoutonGauche
     * 
     * @param album Référence à l'album
     * @param fichier Référence au fichier d'image
     */
	public ControleBoutonGauche(Album alb, Fichier fichier) {
		this.alb = alb;
		this.fichier = fichier;
	}
	
    /**
     * Retourne le fichier d'image
     * 
     * @return Le fichier d'image
     */
	public Fichier getFichier() {
		return fichier;
	}
	

    /**
     * Méthode appelée lors de l'action sur le bouton
     * 
     * @param arg0 L'événement de l'action
     */
	@Override
	public void handle(ActionEvent arg0) {
		FileChooser fc = new FileChooser();
		final File file = fc.showOpenDialog(null); 
		alb.setImageDepart(file.toURI().toString());
		fichier.setF(file);
	}

    /**
     * Méthode de mise à jour de l'observateur
     * 
     * @param obs L'objet obs
     * @param arg argument passé à l'observateur
     */
	@Override
	public void update(Observable obs, Object arg) {
		// Méthode générée automatiquement
		
	}
}
