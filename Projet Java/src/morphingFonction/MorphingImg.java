package morphingFonction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Classe MorphingImg représentant une image sous forme d'une ArrayList de points associés pour le morphing.
 * Permet de charger une image à partir d'un fichier et de manipuler les points associés.
 */
public class MorphingImg {
    private BufferedImage img;  // Image chargée
    private List<Point> tabPoint;  // Liste des points associés à l'image pour le morphing

    /**
     * Constructeur de la classe MorphingImg
     * 
     * @param f le fichier image à charger
     * @param tabPoint la liste des points de morphing associés à l'image
     */
    public MorphingImg(File f, List<Point> tabPoint) throws Exception {
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
    public List<Point> getTabPoint() {
        return tabPoint;
    }

    /**
     * Setter de la liste des points de morphing.
     * 
     * @param tabPoint liste des points de morphing
     */
    public void setTabPoint(List<Point> tabPoint) {
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
     * en fonction des triangles définis par ces points.
     * 
     * @param tabSuivant le tableau de nouveaux points de morphing
     */
    public void imgSuivanteFormeSimple(Point[] tabSuivant) {
        for(int i = 0; i < tabSuivant.length; i++) {
            // Vérifie si le point actuel est différent du nouveau point
            if((this.tabPoint.get(i).getX() != tabSuivant[i].getX()) || (this.tabPoint.get(i).getY() != tabSuivant[i].getY())) {
                // Détermination des bornes pour le rectangle englobant les points du triangle
                int minY = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
                int maxY = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getY(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getY()), this.tabPoint.get((i+1) % tabSuivant.length).getY()), tabSuivant[i].getY());
                int minX = Integer.min(Integer.min(Integer.min(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());
                int maxX = Integer.max(Integer.max(Integer.max(this.tabPoint.get(i).getX(), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length).getX()), this.tabPoint.get((i+1) % tabSuivant.length).getX()), tabSuivant[i].getX());

                // Parcours de tous les points dans le rectangle englobant
                for (int y = minY - 1; y < maxY + 1; y++) {
                    for (int x = minX - 1; x < maxX + 1; x++) {
                        Point p = new Point(x, y);
                        // Vérifie si le point est dans le nouveau triangle
                        if (p.dansTriangle(this.tabPoint.get((i+1) % tabSuivant.length), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length), tabSuivant[i])) {
                            this.img.setRGB(x, y, this.img.getRGB(img.getWidth()/2,img.getHeight()/2));
                        } else {
                            // Vérifie si le point est dans l'ancien triangle
                            if (p.dansTriangle(this.tabPoint.get(i), this.tabPoint.get((i+1) % tabSuivant.length), this.tabPoint.get((i-1+tabSuivant.length) % tabSuivant.length))) {
                                this.img.setRGB(x, y, this.img.getRGB(0,0));
                            }
                        }
                    }
                }
                // Mise à jour du point
                this.tabPoint.set(i, tabSuivant[i]);
            }
        }
    }

    /**
     * Met à jour les points de l'image en fonction d'un tableau de nouveaux points et ajuste les pixels de l'image
     * en fonction d'une forme arrondie définie par ces points.
     * 
     * @param tabSuivant le tableau de nouveaux points de morphing
     */
    public void imgSuivanteFormeArrondie(Point[] tabSuivant) {
        int c = this.img.getRGB(img.getWidth()/2,img.getHeight()/2);
        // Parcours de tous les pixels de l'image
        for (int y = 0; y < this.img.getHeight(); y++) {
            for (int x = 0; x < this.img.getWidth(); x++) {
                Point p = new Point(x, y);
                // Vérifie si le point est dans la figure définie par les nouveaux points
                if (p.dansFigure(tabSuivant))
                    this.img.setRGB(x, y, c);
                else
                    this.img.setRGB(x, y, this.img.getRGB(0,0));
            }
        }
        // Mise à jour des points
        for(int i = 0; i<tabSuivant.length; i++) {
            this.tabPoint.set(i, tabSuivant[i]);
        }
    }

    /**
     * Crée un GIF animé en interpolant entre deux ensembles de points de morphing.
     * 
     * @param tabD liste des points de départ
     * @param tabF liste des points d'arrivée
     * @param nombreImg nombre d'images dans l'animation
     */
    public void creerGif(List<Point> tabD, List<Point> tabF, double nombreImg) {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start("img/testGif.gif");
        e.setRepeat(0);
        e.setFrameRate(60);
        
        // Ajout de quelques images initiales pour commencer le GIF
        for(int i = 0; i < 10; i++) {
            e.addFrame(this.getImg());
        }
        
        // Calcul des incréments de position pour chaque point
        double[] x = new double[tabD.size()];
        double[] y = new double[tabD.size()];
        for (int j = 0; j < tabD.size(); j++) {
            x[j] = (double)(tabF.get(j).getX() - tabD.get(j).getX()) / nombreImg;
            y[j] = (double)(tabF.get(j).getY() - tabD.get(j).getY()) / nombreImg;
        }
        
        // Création des images intermédiaires en interpolant les points
        for(int i = 1; i <= nombreImg; i++) {
            Point[] tabSuivant = new Point[tabD.size()];
            for (int j = 0; j < tabD.size(); j++) {
                tabSuivant[j] = new Point(tabD.get(j).getX() + (int)(i * x[j]), tabD.get(j).getY() + (int)(i * y[j]));
            }
            this.imgSuivanteFormeArrondie(tabSuivant);                
            e.addFrame(this.getImg());
        }

        // Ajout de quelques images finales pour finir le GIF
        for(int i = 0; i < 10; i++) {
            e.addFrame(this.getImg());
        }
        
        e.finish();
    }
}
