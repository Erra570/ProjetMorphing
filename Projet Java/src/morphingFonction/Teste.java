package morphingFonction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import triangle.Point;
import triangle.Triangle;

public class Teste {
	public static void main(String[] args) throws Exception {
		List<Triangle> l1 = new ArrayList<>();
		l1.add(0, new Triangle(new Point(2, 2), new Point(2,498), new Point(498,2)));
		List<Triangle> l2 = new ArrayList<>();
		l2.add(0, new Triangle(new Point(101, 101), new Point(2,498), new Point(498,2)));
		List<Triangle> l3 = new ArrayList<>();
		l3.add(0, new Triangle(new Point(200, 200), new Point(2,498), new Point(498,2)));
		MorphingImgVisage m = new MorphingImgVisage(new File("gImg.jpg"), l1,start);
		m.imgSuivanteVisage(l2, l3, ImageIO.read(new File("img/default.png")), 0.5);
		m.creerImage();
		System.out.println("Fin");
	}
}
