package morphingFonction;

import java.io.File;

public class Teste {
	public static void main(String[] args) throws Exception {
		MatriceImg m = new MatriceImg(new File("gImg.jpg"), new Point[]{new Point(2,2),new Point(2,498),new Point(498,498),new Point(498,2)});
		m.matriceSuivante(new Point[]{new Point(200,200),new Point(200,300),new Point(300,300),new Point(300,200)});
		m.matriceSuivante(new Point[]{new Point(2,2),new Point(2,498),new Point(498,498),new Point(498,2)});
		m.creerImage();
	}
}
