package controle;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import ihm.Fichier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

/**
 * Classe de contrôle pour le bouton de droite permettant de changer l'image de fin
 */
@SuppressWarnings("deprecation")
public class ControleBoutonDroite implements Observer, EventHandler<ActionEvent> {
	
    private Album alb; // Référence à l'album
    public Fichier f;  // Fichier associé au bouton

    /**
     * Constructeur de la classe ControleBoutonDroite
     * 
     * @param alb L'album à contrôler
     * @param f Le fichier associé au bouton
     */
    public ControleBoutonDroite(Album alb, Fichier f) {
        this.alb = alb;
        this.f = f;
    }

    /**
     * Retourne le fichier associé au bouton
     * 
     * @return Le fichier associé au bouton
     */
    public Fichier getF() {
        return f;
    }

    /**
     * Gestionnaire d'événements pour le bouton
     * Ouvre un dialogue de sélection de fichier pour choisir une nouvelle image de fin
     * 
     * @param event L'événement d'action déclenché par le bouton
     */
    @Override
    public void handle(ActionEvent event) {
        FileChooser fc = new FileChooser(); // Création d'un sélecteur de fichiers
        final File file = fc.showOpenDialog(null); // Ouvre le dialogue de sélection de fichier
        if (file != null) { // Vérifie si un fichier a été sélectionné
            alb.setImageFin(file.toURI().toString()); // Met à jour l'image de fin dans l'album
            f.setF(file); // Met à jour le fichier associé
        }
    }

    /**
     * Méthode appelée lorsque l'objet observé est mis à jour
     * 
     * @param o L'objet observable
     * @param arg Argument passé à la méthode notifyObservers
     */
    @Override
    public void update(Observable o, Object arg) {
    }
}
