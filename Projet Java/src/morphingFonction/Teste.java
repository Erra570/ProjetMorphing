package morphingFonction;

import java.io.File;

public class Teste {
	public static void main(String[] args) throws Exception {
		MatriceImg m = new MatriceImg(new File("imgTest2.jpg"), new Point[]{new Point(2,2),new Point(2,7),new Point(7,2)});
		System.out.println(m);
		m.matriceSuivante(new Point[]{new Point(4,4),new Point(2,7),new Point(7,2)});
		System.out.println(m);
		m.creerImage();
	}
}
