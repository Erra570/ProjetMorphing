package morphingFonction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
public class MatriceImg {
	private BufferedImage img;
	private List<Point> tabPoint;
	
	public MatriceImg(File f, List<Point> tabPoint) throws Exception {
		this.img = ImageIO.read(f);
		this.tabPoint = new ArrayList<>();
		for(int i = 0; i<tabPoint.size(); i++)
			this.tabPoint.add(tabPoint.get(i)); 
	}
	
	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public List<Point> getTabPoint() {
		return tabPoint;
	}

	public void setTabPoint(List<Point> tabPoint) {
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
			if((this.tabPoint.get(i).getX() != tabSuivant[i].getX()) || (this.tabPoint.get(i).getY() != tabSuivant[i].getY())) {
				int minY = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
				int maxY = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
				int minX = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());
				int maxX = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());
				for (int y = minY - 1; y < maxY + 1; y++) {
					for (int x = minX - 1; x < maxX + 1; x++) {
						Point p = new Point(x,y);
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
