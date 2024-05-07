package morphingFonction;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;
public class MatriceImg {
	private Color[][] matriceCouleur;
	
	public MatriceImg(File f) throws Exception {
		BufferedImage img = ImageIO.read(f);
		matriceCouleur = new Color[img.getHeight()][img.getWidth()];
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				matriceCouleur[y][x] = new Color(img.getRGB(x,y), true);
		    }
		}
	}

	public Color[][] getMatrice() {
		return matriceCouleur;
	}

	public void setMatrice(Color[][] matriceCouleur) {
		this.matriceCouleur = matriceCouleur;
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
	
	
}
