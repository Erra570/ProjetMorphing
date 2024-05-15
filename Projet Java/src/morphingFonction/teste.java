package morphingFonction;

import java.io.File;

public class teste {
	
	public static void main(String[] args) throws Exception {
		double nombre = 60;
		Point[] tab = new Point[]{new Point(2,2),new Point(2,498),new Point(498,498),new Point(498,2)};
		Point[] tabD = new Point[]{new Point(2,2),new Point(2,498),new Point(498,498),new Point(498,2)};
		Point[] tabF = new Point[]{new Point(250,2),new Point(2,498),new Point(498,498),new Point(250,2)};
		MatriceImg m = new MatriceImg(new File("gImg.jpg"), tabD);
		
		AnimatedGifEncoder e = new AnimatedGifEncoder();
		e.start("testGif.gif");
		e.setRepeat(0);
		e.setFrameRate(60);
		e.addFrame(m.getImg());
		
		double[] x = new double[tabD.length];
		double[] y = new double[tabD.length];
		for (int j=0; j<tabD.length; j++) {
			x[j] = (double)(tabF[j].getX() - tabD[j].getX())/nombre;
			y[j] = (double)(tabF[j].getY() - tabD[j].getY())/nombre;
		}
		
		for(int i=1; i<nombre; i++) {
			Point[] tabSuivant = new Point[tabD.length];
			for (int j=0; j<tabD.length; j++) {
				tabSuivant[j] = new Point(tab[j].getX() + (int)(i*x[j]),tab[j].getY() + (int)(i*y[j]));
			}
			m.matriceSuivante(tabSuivant);				
			e.addFrame(m.getImg());
		}
		m.matriceSuivante(tabF);
		m.creerImage();
		e.addFrame(m.getImg());
		
		e.finish();
	}
}
