package morphingFonction;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class MatriceImg {
	private Color[][] matriceCouleur;
	private Point[] tabPoint;
	
	public MatriceImg(File f, Point[] tabPoint) throws Exception {
		BufferedImage img = ImageIO.read(f);
		matriceCouleur = new Color[img.getHeight()][img.getWidth()];
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				matriceCouleur[y][x] = new Color(img.getRGB(x,y), true);
		    }
		}
		this.tabPoint = tabPoint; 
	}

	public Color[][] getMatrice() {
		return matriceCouleur;
	}

	public void setMatrice(Color[][] matriceCouleur) {
		this.matriceCouleur = matriceCouleur;
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
		for (int y = 0; y < matriceCouleur.length; y++) {
			for (int x = 0; x < matriceCouleur[0].length; x++) {
				r += "(" + matriceCouleur[y][x].getRed() + "," + matriceCouleur[y][x].getGreen() + "," + matriceCouleur[y][x].getBlue() + ")";
		    }
			r += "\n";
		}
		return r;
	}
	
	public void creerImage() {
		BufferedImage image = new BufferedImage(matriceCouleur[0].length, matriceCouleur.length, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < matriceCouleur.length; y++) {
			for (int x = 0; x < matriceCouleur[0].length; x++) {
				image.setRGB(x, y, matriceCouleur[y][x].getRGB());
		    }
		}
		try {
			ImageIO.write(image, "JPG", new File("imgTest5.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void matriceSuivante(Point[] tabSuivant) {
		for(int i = 0; i<tabSuivant.length; i++) {
			if(this.tabPoint[i] != tabSuivant[i]) {
				Color c;
				if(this.tabPoint[i].getX() != tabSuivant[i].getX()) {
					if(this.tabPoint[i].getX() < tabSuivant[i].getX()) {
						c = this.matriceCouleur[this.tabPoint[i].getY()][this.tabPoint[i].getX() - 1];
					} else {
						c = this.matriceCouleur[this.tabPoint[i].getY()][this.tabPoint[i].getX() + 1];						
					}
					for (int y = 0; y < matriceCouleur.length; y++) {
						for (int x = 0; x < matriceCouleur[0].length; x++) {
							Point p = new Point(x,y);
							if (p.dansTriangle(this.tabPoint[i], this.tabPoint[(i+1) % tabSuivant.length], tabSuivant[i]))
								this.matriceCouleur[y][x] = c;
							if (p.dansTriangle(this.tabPoint[i], this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length], tabSuivant[i]))
								this.matriceCouleur[y][x] = c;
						}
					}
				} else {
					if(this.tabPoint[i].getY() < tabSuivant[i].getY()) {
						c = this.matriceCouleur[this.tabPoint[i].getY() - 1][this.tabPoint[i].getX()];
					} else {
						c = this.matriceCouleur[this.tabPoint[i].getY() + 1][this.tabPoint[i].getX()];
					}
					for (int y = 0; y < matriceCouleur.length; y++) {
						for (int x = 0; x < matriceCouleur[0].length; x++) {
							Point p = new Point(x,y);
							if (p.dansTriangle(this.tabPoint[i], this.tabPoint[(i+1) % tabSuivant.length], tabSuivant[i]))
								this.matriceCouleur[y][x] = c;
							if (p.dansTriangle(this.tabPoint[i], this.tabPoint[(i-1+tabSuivant.length) % tabSuivant.length], tabSuivant[i]))
								this.matriceCouleur[y][x] = c;
						}
					}
				}
			}
		}
	}
}
