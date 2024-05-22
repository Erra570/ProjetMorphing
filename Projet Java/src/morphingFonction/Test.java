package morphingFonction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import first.QCurve;

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
		
		List<QCurve> tabD = new ArrayList<>();
        tabD.add(0, new QCurve(2,2,2,2,2,498));
        tabD.add(1, new QCurve(2,498,2,498,498,498));
        tabD.add(2, new QCurve(498,498,498,498,498,2));
        tabD.add(3, new QCurve(498,2,498,2,2,2));
        
        List<QCurve> tabF = new ArrayList<>();
        tabF.add(0, new QCurve(250,2,2,2,2,250));
        tabF.add(1, new QCurve(2,250,2,498,250,498));
        tabF.add(2, new QCurve(250,498,498,498,498,250));
        tabF.add(3, new QCurve(498,250,498,2,250,2));
        
        double nombreImg = 60;
        int i = 25;
        
        QCurve[] tabIncrement = new QCurve[tabD.size()];
        for (int j = 0; j < tabD.size(); j++) {
            double XDepart = (tabF.get(j).getXDepart() - tabD.get(j).getXDepart()) / nombreImg;
            double YDepart = (tabF.get(j).getYDepart() - tabD.get(j).getYDepart()) / nombreImg;
            double XControl = (tabF.get(j).getXControl() - tabD.get(j).getXControl()) / nombreImg;
            double YControl = (tabF.get(j).getYControl() - tabD.get(j).getYControl()) / nombreImg;
            double XFin = (tabF.get(j).getXFin() - tabD.get(j).getXFin()) / nombreImg;
            double YFin = (tabF.get(j).getYFin() - tabD.get(j).getYFin()) / nombreImg;

            tabIncrement[j] = new QCurve(XDepart, YDepart, XControl, YControl, XFin, YFin);
        }

        List<QCurve> tabSuivant = new ArrayList<>();
        for (int j = 0; j < tabD.size(); j++) {
            double XDepart = tabD.get(j).getXDepart() + (i * tabIncrement[j].getXDepart());
            double YDepart = tabD.get(j).getYDepart() + (i * tabIncrement[j].getYDepart());
            double XControl = tabD.get(j).getXControl() + (i * tabIncrement[j].getXControl());
            double YControl = tabD.get(j).getYControl() + (i * tabIncrement[j].getYControl());
            double XFin = tabD.get(j).getXFin() + (i * tabIncrement[j].getXFin());
            double YFin = tabD.get(j).getYFin() + (i * tabIncrement[j].getYFin());

            if (j == 0) {
                tabSuivant.add(new QCurve(XDepart, YDepart, XControl, YControl, XFin, YFin));
            } else {
                tabSuivant.add(new QCurve(tabSuivant.get(j-1).getXFin(), tabSuivant.get(j-1).getYFin(), XControl, YControl, XFin, YFin));
                System.out.println(XDepart + "," + tabSuivant.get(j).getXFin() + "," + XFin);
            }
        }
        /*tabF.add(0, new QCurve(250,2,2,2,2,250));
        tabF.add(0, new QCurve(2,250,2,498,250,498));
        tabF.add(0, new QCurve(250,498,498,498,498,250));
        tabF.add(0, new QCurve(498,250,498,2,250,2));*/
		
		MorphingImg m = new MorphingImg(new File("img/gImg.jpg"), tabD);
		
		m.imgSuivanteFormeArrondie(tabSuivant);
		m.creerImage();
		//m.creerGif(tabD, tabF, 60);
		
		System.out.println("Traitement terminé!");
	}
}
