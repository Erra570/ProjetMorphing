package morphingFonction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class MatriceImg {
	private BufferedImage img;
	private Point[] tabPoint;
	
	public MatriceImg(File f, Point[] tabPoint) throws Exception {
		this.img = ImageIO.read(f);
		this.tabPoint = tabPoint; 
	}

	public Point[] getTabPoint() {
		return tabPoint;
	}

	public void setTabPoint(Point[] tabPoint) {
		this.tabPoint = tabPoint;
	}

	@Override
	public String toString() {
		String r = "";
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				r += "(" + img.getRGB(x,y) + ")";
		    }
			r += "\n";
		}
		return r;
	}
	
	public void creerImage() {
		try {
			ImageIO.write(img, "JPG", new File("imgTest5.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void matriceSuivante(Point[] tabSuivant) {
		for(int i = 0; i<tabSuivant.length; i++) {
			if((this.tabPoint[i].getX() != tabSuivant[i].getX()) || (this.tabPoint[i].getY() != tabSuivant[i].getY())) {
				int minY = Integer.min(Integer.min(Integer.min(this.tabPoint[i].getY(), this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length].getY()), this.tabPoint[(i+1) % tabSuivant.length].getY()), tabSuivant[i].getY());
				int maxY = Integer.max(Integer.max(Integer.max(this.tabPoint[i].getY(), this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length].getY()), this.tabPoint[(i+1) % tabSuivant.length].getY()), tabSuivant[i].getY());
				int minX = Integer.min(Integer.min(Integer.min(this.tabPoint[i].getX(), this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length].getX()), this.tabPoint[(i+1) % tabSuivant.length].getX()), tabSuivant[i].getX());
				int maxX = Integer.max(Integer.max(Integer.max(this.tabPoint[i].getX(), this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length].getX()), this.tabPoint[(i+1) % tabSuivant.length].getX()), tabSuivant[i].getX());
				for (int y = minY; y < maxY; y++) {
					for (int x = minX; x < maxX; x++) {
						Point p = new Point(x,y);
						if (p.dansTriangle(this.tabPoint[(i+1) % tabSuivant.length], this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length], tabSuivant[i]))
							this.img.setRGB(x, y, this.img.getRGB(img.getWidth()/2,img.getHeight()/2));
						else {
							if (p.dansTriangle(this.tabPoint[i], this.tabPoint[(i+1) % tabSuivant.length], this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length]))
								this.img.setRGB(x, y, this.img.getRGB(0,0));
						}
					}
				}
				this.tabPoint[i] = tabSuivant[i];
			}
		}
	}
}
