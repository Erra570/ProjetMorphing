package ihm;

import java.io.File;

/**
 * Classe représentant un fichier d'image
 */
public class Fichier {
    private File f; // Attribut représentant le fichier

    /**
     * Constructeur par défaut de la classe Fichier
     * Initialise un nouvel objet Fichier sans fichier associé
     */
    public Fichier() {
    }

    /**
     * Retourne le fichier associé
     * 
     * @return Le fichier associé
     */
    public File getF() {
        return f;
    }

    /**
     * Met à jour le fichier associé
     * 
     * @param f Le nouveau fichier à associer
     */
    public void setF(File f) {
        this.f = f;
    }
}
