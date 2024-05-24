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
 * Classe de contrôle pour le bouton de droite permettant de sélectionner une image de fin
 * et de la mettre à jour dans l'album.
 * 
 * @auteur Reignier Arnaud
 * @auteur Vigneron Bastien
 * @auteur Finana Tom
 * @auteur Fontaine Nicolas
 * @auteur Charrier Simon
 */
@SuppressWarnings("deprecation")
public class ControleBoutonDroite implements Observer, EventHandler<ActionEvent> {
	
    private Album album; // Référence à l'album
    private Fichier fichier; // Référence au fichier d'image

    /**
     * Constructeur de la classe ControleBoutonDroite
     * 
     * @param album Référence à l'album
     * @param fichier Référence au fichier d'image
     */
    public ControleBoutonDroite(Album album, Fichier fichier) {
        this.album = album;
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
        FileChooser selecteurFichier = new FileChooser();
        final File fichierChoisi = selecteurFichier.showOpenDialog(null); 
        album.setImageFin(fichierChoisi.toURI().toString());
        fichier.setF(fichierChoisi);
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