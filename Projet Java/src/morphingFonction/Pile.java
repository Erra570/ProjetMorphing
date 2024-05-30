package morphingFonction;

/**
 * La classe Pile représente une pile de points de morphing.
 */
public class Pile {

    private PointMorphing[] tab; // Tableau pour stocker les points de morphing
    private int courant; // Indice du sommet de la pile

    /**
     * Constructeur par défaut de la classe Pile.
     * Initialise une pile vide avec une capacité initiale.
     */
    public Pile() { 
        this.tab = new PointMorphing[500*500];
        courant = -1;
    }

    /**
     * Empile un nouveau point de morphing sur la pile.
     * @param x Coordonnée x du point à empiler.
     * @param y Coordonnée y du point à empiler.
     */
    public void empiler(int x, int y) {
        courant++;
        this.tab[courant] =  new PointMorphing(x,y);
    }

    /**
     * Dépile le dernier point de morphing de la pile.
     */
    public void depiler() {
        if (courant > -1)
            courant--;
    }

    /**
     * Renvoie le point de morphing situé au sommet de la pile.
     * @return PointMorphing au sommet de la pile, ou null si la pile est vide.
     */
    public PointMorphing sommet() {
        return (this.vide()) ? null : this.tab[courant];
    }

    /**
     * Vérifie si la pile est vide.
     * @return true si la pile est vide, sinon false.
     */
    public boolean vide() {
        return (this.courant == -1);
    }
}
