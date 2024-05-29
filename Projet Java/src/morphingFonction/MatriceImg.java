package morphingFonction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * Classe MatriceImg représentant une image sous forme d'une ArrayList de points associés pour le morphing.
 * Permet de charger une image à partir d'un fichier et de manipuler les points associés.
 */
public class MatriceImg {
	private BufferedImage img;
	private List<PointMorphing> tabPoint;
	
    /**
     * Constructeur de la classe MatriceImg
     * 
     * @param f le fichier image à charger
     * @param tabPoint la liste des points de morphing associés à l'image
     */
	public MatriceImg(File f, List<PointMorphing> tabPoint) throws Exception {
		this.img = ImageIO.read(f);
		this.tabPoint = new ArrayList<>();
		for(int i = 0; i<tabPoint.size(); i++)
			this.tabPoint.add(tabPoint.get(i)); 
	}
	
    /**
     * Getter de l'image.
     * 
     * @return l'image BufferedImage
     */
	public BufferedImage getImg() {
		return img;
	}

	/**
     * Setter de l'image.
     * 
     * @param img l'image BufferedImage à définir
     */
	public void setImg(BufferedImage img) {
		this.img = img;
	}

    /**
     * Getter de la liste des points de morphing.
     * 
     * @return la liste
     */
	public List<PointMorphing> getTabPoint() {
		return tabPoint;
	}

    /**
     * Setter de la liste des points de morphing.
     * 
     * @param tabPoint liste des points de morphing
     */
	public void setTabPoint(List<PointMorphing> tabPoint) {
		this.tabPoint = tabPoint;
	}

	/**
     * Retourne une représentation sous forme de chaîne de caractères de l'image.
     * 
     * @return une chaîne de caractères représentant l'image
     */
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
	
    /**
     * Crée une nouvelle image en enregistrant l'image actuelle dans un fichier.
     */
	public void creerImage() {
		try {
			ImageIO.write(img, "JPG", new File("imgTest5.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    /**
     * Met à jour les points de l'image en fonction d'un tableau de nouveaux points et ajuste les pixels de l'image
     * en fonction des triangles définis par ces points.
     * 
     * @param tabSuivant le tableau de nouveaux points de morphing
     */
	public void matriceSuivante(PointMorphing[] tabSuivant) {
		for(int i = 0; i<tabSuivant.length; i++) {
			if((this.tabPoint.get(i).getX() != tabSuivant[i].getX()) || (this.tabPoint.get(i).getY() != tabSuivant[i].getY())) {
				int minY = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
				int maxY = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
				int minX = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());
				int maxX = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());
				for (int y = minY - 1; y < maxY + 1; y++) {
					for (int x = minX - 1; x < maxX + 1; x++) {
						PointMorphing p = new PointMorphing(x,y);
						if (p.dansTriangle(this.tabPoint.get((i+1) % tabSuivant.length), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length), tabSuivant[i]))
							this.img.setRGB(x, y, this.img.getRGB(img.getWidth()/2,img.getHeight()/2));
						else {
							if (p.dansTriangle(this.tabPoint.get(i), this.tabPoint.get((i+1) % tabSuivant.length), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length)))
								this.img.setRGB(x, y, this.img.getRGB(0,0));
						}
					}
				}
				this.tabPoint.set(i, tabSuivant[i]);
			}
		}
	}
}
