package morphingFonction;

import java.awt.image.BufferedImage;
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
		List<Triangle> l4 = new ArrayList<>();
		l4.add(0, new Triangle(new Point(150, 150), new Point(2,498), new Point(498,2)));
		MorphingImgVisage m = new MorphingImgVisage(new File("gImg.jpg"), l1);
		BufferedImage img;
		//img = m.imgSuivanteVisage(l2, l3, ImageIO.read(new File("img/default.png")), 0.5);
		//img = m.imgSuivanteVisage(l4, l3, ImageIO.read(new File("img/default.png")), 0.75);
		//m.creerImage(img);
		m.creerGif(l1,l3,new File("img/default.png"),60);
		System.out.println("Fin");
	}
}
