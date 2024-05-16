package morphingFonction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Teste {
	
	public static void main(String[] args) throws Exception {
		double nombre = 60;
		
		List<Point> tabD = new ArrayList<>();
		tabD.add(0, new Point(2,2));
		tabD.add(1, new Point(2,498));
		tabD.add(2, new Point(498,498));
		tabD.add(3, new Point(498,2));
		
		List<Point> tabF = new ArrayList<>();
		tabF.add(0, new Point(250,2));
		tabF.add(1, new Point(2,498));
		tabF.add(2, new Point(498,498));
		tabF.add(3, new Point(250,2));
		
		MatriceImg m = new MatriceImg(new File("gImg.jpg"), tabD);
		
		AnimatedGifEncoder e = new AnimatedGifEncoder();
		e.start("testGif.gif");
		e.setRepeat(0);
		e.setFrameRate(60);
		e.addFrame(m.getImg());
		double[] x = new double[tabD.size()];
		double[] y = new double[tabD.size()];
		for (int j=0; j<tabD.size(); j++) {
			x[j] = (double)(tabF.get(j).getX() - tabD.get(j).getX())/nombre;
			y[j] = (double)(tabF.get(j).getY() - tabD.get(j).getY())/nombre;
		}
		
		for(int i=1; i<=nombre; i++) {
			Point[] tabSuivant = new Point[tabD.size()];
			for (int j=0; j<tabD.size(); j++) {
				tabSuivant[j] = new Point(tabD.get(j).getX() + (int)(i*x[j]),tabD.get(j).getY() + (int)(i*y[j]));
			}
			m.matriceSuivante(tabSuivant);				
			e.addFrame(m.getImg());
		}
		
		e.finish();
	}
}
