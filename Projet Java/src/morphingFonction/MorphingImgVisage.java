package morphingFonction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.Color;
import triangle.Point;
import triangle.Triangle;

/**
 * Classe MorphingImg représentant une image sous forme d'une ArrayList de points associés pour le morphing.
 * Permet de charger une image à partir d'un fichier et de manipuler les points associés.
 */
public class MorphingImgVisage {
    private BufferedImage img;  // Image chargée
    private List<Triangle> tabPoint;  // Liste des points associés à l'image pour le morphing

    /**
     * Constructeur de la classe MorphingImg
     * 
     * @param f le fichier image à charger
     * @param tabPoint la liste des points de morphing associés à l'image
     */
    public MorphingImgVisage(File f, List<Triangle> tabPoint) throws Exception {
        this.img = ImageIO.read(f);  // Lecture de l'image à partir du fichier
        this.tabPoint = new ArrayList<>();
        for(int i = 0; i<tabPoint.size(); i++) {
            this.tabPoint.add(tabPoint.get(i));  // Copie des points de morphing
        }
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
    public List<Triangle> getTabPoint() {
        return tabPoint;
    }

    /**
     * Setter de la liste des points de morphing.
     * 
     * @param tabPoint liste des points de morphing
     */
    public void setTabPoint(List<Triangle> tabPoint) {
        this.tabPoint = tabPoint;
    }

    /**
     * Crée une nouvelle image en enregistrant l'image actuelle dans un fichier.
     */
    public void creerImage() {
        try {
            ImageIO.write(img, "JPG", new File("img/imgTest5.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les points de l'image en fonction d'un tableau de nouveaux points et ajuste les pixels de l'image
     * en fonction d'une forme arrondie définie par ces points.
     * 
     * @param tabSuivant le tableau de nouveaux points de morphing
     */
    public void imgSuivanteVisage(List<Triangle> tabSuivant, List<Triangle> tabFin, BufferedImage imgFin, double t) {
    	BufferedImage imgSuivant = new BufferedImage(this.img.getWidth(), this.img.getHeight(), BufferedImage.TYPE_INT_RGB);
    	for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				for (int i = 0; i<tabPoint.size(); i++) {
					PointMorphing p = new PointMorphing(x,y);
					if (p.dansTriangle(tabPoint.get(i).getPoint_1().transtypage(), tabPoint.get(i).getPoint_2().transtypage(), tabPoint.get(i).getPoint_3().transtypage()))  {
						Set<Point> ensembleP = tabPoint.get(i).pointNouveauTriangle(new Point(x,y), tabSuivant.get(i));
						Set<Point> ensembleFin = tabPoint.get(i).pointNouveauTriangle(new Point(x,y), tabSuivant.get(i));
						Color cFin = couleurMoyenne(imgFin, ensembleFin);
						Color cPoint = new Color(img.getRGB((int) p.getX(), (int) p.getY()), true);
				        int r = (int) ((1 - t) * cPoint.getRed() + t * cFin.getRed());
				        int g = (int) ((1 - t) * cPoint.getGreen() + t * cFin.getGreen());
				        int b = (int) ((1 - t) * cPoint.getBlue() + t * cFin.getBlue());
				        Color c = new Color(r,g,b);
				        for(Point e : ensembleP) {
				    		imgSuivant.setRGB((int) e.getX(), (int) e.getY(), c.getRGB());
				    	}
					}
				}
			}
    	}
    	this.img = imgSuivant;
    }
    
    public Color couleurMoyenne(BufferedImage img, Set<Point> ensemble) {
    	int r = 0;
    	int g = 0;
    	int b = 0;
    	for(Point p : ensemble) {
    		Color c = new Color(img.getRGB((int) p.getX(), (int) p.getY()), true);
        	r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
    	}
    	return new Color(r/ensemble.size(), g/ensemble.size(), b/ensemble.size());
    }
    
    
    private Point calculMoyenneCoord(Point pDebut, Point pFin, double t) {
        double x = Math.round((1 - t) * pDebut.getX() + t * pFin.getX());
        double y = Math.round((1 - t) * pDebut.getY() + t * pFin.getY());
        return new Point(x, y);
    }
    
    
    /**
     * 
     * @param tabD liste des points de départ
     * @param tabF liste des points de fin
     * @param t entre 0 et 1
     * @return la moyenne des deux tableaux
     * @throws Exception 
     */
    public List<Triangle> triangleMoyenne(List<Triangle> tabD, List<Triangle> tabF, double t) throws Exception {
    	List<Triangle> res = new ArrayList<>();
        for (int i = 0; i < tabD.size(); i++) {
            Triangle triD = tabD.get(i);
            Triangle triF = tabF.get(i);
            
            Point p1 = calculMoyenneCoord(triD.getPoint_1(), triF.getPoint_1(), t);
            Point p2 = calculMoyenneCoord(triD.getPoint_2(), triF.getPoint_2(), t);
            Point p3 = calculMoyenneCoord(triD.getPoint_3(), triF.getPoint_3(), t);
            
            res.add(new Triangle(p1, p2, p3));
        }
        return res;
    }
    
    /**
     * Crée un GIF animé en interpolant entre deux ensembles de points de morphing.
     * 
     * @param tabD liste des points de départ
     * @param tabF liste des points d'arrivée
     * @param nombreImg nombre d'images dans l'animation
     * @throws Exception 
     */
    public void creerGif(List<Triangle> tabD, List<Triangle> tabF, File f, double nombreImg) throws Exception {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start("img/testGif.gif");  // Début de la création du GIF
        e.setRepeat(0);  // Répéter l'animation en boucle
        e.setFrameRate(60);  // Définir la cadence d'images

        // Ajout de quelques images initiales pour commencer le GIF
        for(int i = 0; i < 10; i++) {
            e.addFrame(this.getImg());
        }
        
        // Création des images intermédiaires en interpolant les points
        for(int i = 1; i <= nombreImg; i++) {
            List<Triangle> tabSuivant = triangleMoyenne(tabD, tabF, (1/nombreImg) * i);
            this.imgSuivanteVisage(tabSuivant, tabF, ImageIO.read(f), (1/nombreImg) * i);  // Mise à jour de l'image avec les nouveaux points                
            e.addFrame(this.getImg());  // Ajout de l'image mise à jour au GIF

            System.out.println((i*100)/nombreImg);  // Affichage de la progression
            if (i == 20)
            	this.creerImage();  // Création d'une image intermédiaire
        }

        // Ajout de quelques images finales pour finir le GIF
        for(int i = 0; i < 10; i++) {
            e.addFrame(this.getImg());
        }
        
        e.finish();  // Finalisation du GIF
    }
}
