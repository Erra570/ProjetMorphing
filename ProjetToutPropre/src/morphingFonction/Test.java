package morphingFonction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

import first.QCurve;

/**
 * Classe Test contenant la méthode main pour tester le morphing d'images.
 */
public class Test {

	/**
	 * Méthode principale pour exécuter le test de morphing d'images.
	 * 
	 * @param args les arguments de la ligne de commande
	 * @throws Exception si une erreur se produit lors de la lecture du fichier ou
	 *                   de la manipulation de l'image
	 */
	public static void main(String[] args) throws Exception {

		List<QCurve> tabD = new ArrayList<>();
		tabD.add(0, new QCurve(2.0, 2.0, 2.0, 2.0, 2.0, 498.0, Color.BLUE));
		tabD.add(1, new QCurve(2, 498, 2, 498, 498, 498, Color.BLUE));
		tabD.add(2, new QCurve(498, 498, 498, 498, 498, 2, Color.BLUE));
		tabD.add(3, new QCurve(498, 2, 498, 2, 2, 2, Color.BLUE));

		List<QCurve> tabF = new ArrayList<>();
		tabF.add(0, new QCurve(250, 2, 2, 2, 2, 250, Color.BLUE));
		tabF.add(1, new QCurve(2, 250, 2, 498, 250, 498, Color.BLUE));
		tabF.add(2, new QCurve(250, 498, 498, 498, 498, 250, Color.BLUE));
		tabF.add(3, new QCurve(498, 250, 498, 2, 250, 2, Color.BLUE));

		//double nombreImg = 60;
		//int i = 25;

		
		/*
		 * tabF.add(0, new QCurve(250,2,2,2,2,250)); tabF.add(0, new
		 * QCurve(2,250,2,498,250,498)); tabF.add(0, new
		 * QCurve(250,498,498,498,498,250)); tabF.add(0, new
		 * QCurve(498,250,498,2,250,2));
		 */

		MorphingImg m = new MorphingImg(new File("img/gImg.jpg"), tabD);

		//m.imgSuivanteFormeArrondie(tabSuivant);
		//m.creerImage();
		 m.creerGif(tabD, tabF, 60);

		System.out.println("Traitement terminé!");
	}
}
