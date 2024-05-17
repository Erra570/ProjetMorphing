package morphingFonction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Test contenant la méthode main pour tester le morphing d'images.
 */
public class Test {
	
	/**
     * Méthode principale pour exécuter le test de morphing d'images.
     * 
     * @param args les arguments de la ligne de commande
     * @throws Exception si une erreur se produit lors de la lecture du fichier ou de la manipulation de l'image
     */
	public static void main(String[] args) throws Exception {
		
		List<Point> tabD = new ArrayList<>();
        tabD.add(0, new Point(2,2));
        tabD.add(1, new Point(2,498));
        tabD.add(2, new Point(2,250));
        tabD.add(3, new Point(498,498));
        tabD.add(4, new Point(250,498));
        tabD.add(5, new Point(498,2));
        tabD.add(6, new Point(498,250));
        tabD.add(7, new Point(2,2));
        tabD.add(8, new Point(250,2));
        
        List<Point> tabF = new ArrayList<>();
        tabF.add(0, new Point(250,2));
        tabF.add(1, new Point(2,250));
        tabF.add(2, new Point(2,2));
        tabF.add(3, new Point(250,498));
        tabF.add(4, new Point(2,498));
        tabF.add(5, new Point(498,250));
        tabF.add(6, new Point(498,498));
        tabF.add(7, new Point(250,2));
        tabF.add(8, new Point(498,2));
		
		MorphingImg m = new MorphingImg(new File("img/gImg.jpg"), tabD);
		
		m.creerGif(tabD, tabF, 60);
		
		System.out.println("Traitement terminé!");
	}
}
