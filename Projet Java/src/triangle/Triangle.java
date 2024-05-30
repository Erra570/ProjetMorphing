package triangle;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente un triangle dans un plan cartésien.
 */
public class Triangle {
    private Point point_1;
    private Point point_2;
    private Point point_3;
    private CercleCirconscrit cercle;

    /**
     * Constructeur d'un triangle à partir de trois points.
     * @param p1 Le premier point du triangle.
     * @param p2 Le deuxième point du triangle.
     * @param p3 Le troisième point du triangle.
     * @throws Exception Si les points sont alignés, un triangle ne peut pas être formé.
     */
    public Triangle(Point p1, Point p2, Point p3) throws Exception {
        this.point_1 = p1;
        this.point_2 = p2;
        this.point_3 = p3;
        this.cercle = calculCercleCirconscrit();
    }

    private CercleCirconscrit calculCercleCirconscrit() throws Exception {
        double a = point_1.getX() * point_1.getX() + point_1.getY() * point_1.getY();
        double b = point_2.getX() * point_2.getX() + point_2.getY() * point_2.getY();
        double c = point_3.getX() * point_3.getX() + point_3.getY() * point_3.getY();
        double delta = point_1.getX() * (point_2.getY() - point_3.getY())
                - point_1.getY() * (point_2.getX() - point_3.getX())
                + (point_2.getX() * point_3.getY() - point_2.getY() * point_3.getX());
        if (delta == 0) {
            throw new Exception("Les points sont collinéaires");
        }
        double detCentreX = a * (point_2.getY() - point_3.getY()) - point_1.getY() * (b - c)
                + (b * point_3.getY() - point_2.getY() * c);
        double detCentreY = a * (point_2.getX() - point_3.getX()) - point_1.getX() * (b - c)
                + (b * point_3.getX() - point_2.getX() * c);
        double xCentre = detCentreX / (2 * delta);
        double yCentre = -detCentreY / (2 * delta);

        Point centre = new Point(xCentre, yCentre);
        
        double rayon = Math.abs(point_1.distance(point_2)*point_2.distance(point_3)*point_3.distance(point_1)/(2*delta));

        return new CercleCirconscrit(centre, rayon);
    }

    /**
     * Obtient le cercle circonscrit à ce triangle.
     * @return Le cercle circonscrit à ce triangle.
     */
    public CercleCirconscrit getCercle() {
        return this.cercle;
    }

    /**
     * Obtient le premier point du triangle.
     * @return Le premier point du triangle.
     */
    public Point getPoint_1() {
        return point_1;
    }

    /**
     * Obtient le deuxième point du triangle.
     * @return Le deuxième point du triangle.
     */
    public Point getPoint_2() {
        return point_2;
    }

    /**
     * Obtient le troisième point du triangle.
     * @return Le troisième point du triangle.
     */
    public Point getPoint_3() {
        return point_3;
    }

    /**
     * Méthode pour vérifier si un point est à l'intérieur du triangle.
     * @param point Le point à tester.
     * @return true si le point est à l'intérieur du triangle, sinon false.
     */
    public boolean contient(Point point) {
        double x = point.getX();
        double y = point.getY();

        double AB_x = point_2.getX() - point_1.getX();
        double AB_y = point_2.getY() - point_1.getY();
        double AM_x = x - point_1.getX();
        double AM_y = y - point_1.getY();
        double AC_x = point_3.getX() - point_1.getX();
        double AC_y = point_3.getY() - point_1.getY();

        double BA_x = point_1.getX() - point_2.getX();
        double BA_y = point_1.getY() - point_2.getY();
        double BM_x = x - point_2.getX();
        double BM_y = y - point_2.getY();
        double BC_x = point_3.getX() - point_2.getX();
        double BC_y = point_3.getY() - point_2.getY();

        double CA_x = point_1.getX() - point_3.getX();
        double CA_y = point_1.getY() - point_3.getY();
        double CM_x = x - point_3.getX();
        double CM_y = y - point_3.getY();
        double CB_x = point_2.getX() - point_3.getX();
        double CB_y = point_2.getY() - point_3.getY();

        double cross1 = vectorCrossProduct(AB_x, AB_y, AM_x, AM_y) * vectorCrossProduct(AM_x, AM_y, AC_x, AC_y);
        double cross2 = vectorCrossProduct(BA_x, BA_y, BM_x, BM_y) * vectorCrossProduct(BM_x, BM_y, BC_x, BC_y);
        double cross3 = vectorCrossProduct(CA_x, CA_y, CM_x, CM_y) * vectorCrossProduct(CM_x, CM_y, CB_x, CB_y);

        return cross1 >= 0 && cross2 >= 0 && cross3 >= 0;
    }

    private double vectorCrossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - y1 * x2;
    }
    
    /**
     * Méthode pour trouver les nouveaux points dans un triangle.
     * @param p Le point à ajouter.
     * @param t Le triangle.
     * @return Un ensemble de points représentant les nouveaux points dans le triangle.
     */
    public Set<Point> pointNouveauTriangle (Point p, Triangle t) {
        Set<Point> ensemble = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                double denominator = ((this.point_2.getY() - this.point_3.getY()) * (this.point_1.getX() - this.point_3.getX()) + (this.point_3.getX() - this.point_2.getX()) * (this.point_1.getY() - this.point_3.getY()));
                double u = ((this.point_2.getY() - this.point_3.getY()) * (p.getX() + (1. / 10. * i) - this.point_3.getX()) + (this.point_3.getX() - this.point_2.getX()) * (p.getY() + (1. / 10. * j) - this.point_3.getY())) / denominator;
                double v = ((this.point_3.getY() - this.point_1.getY()) * (p.getX() + (1. / 10. * i) - this.point_3.getX()) + (this.point_1.getX() - this.point_3.getX()) * (p.getY() + (1. / 10. * j) - this.point_3.getY())) / denominator;
                double w = 1 - u - v;
                if (u >= 0 && v >= 0 && w >= 0) {
                    double[] tabBarycentrique = new double[]{u, v, w};
                    
                    int x = (int) Math.round(tabBarycentrique[0] * t.point_1.getX() + tabBarycentrique[1] * t.point_2.getX() + tabBarycentrique[2] * t.point_3.getX());
                    int y = (int) Math.round(tabBarycentrique[0] * t.point_1.getY() + tabBarycentrique[1] * t.point_2.getY() + tabBarycentrique[2] * t.point_3.getY());
                    
                    Point m = new Point(x, y);
                    boolean b = false;
                    for (Point n : ensemble) {
                        if (!b && n.getX() == m.getX() && n.getY() == m.getY())
                            b = !b;
                    }
                    if (!b)
                        ensemble.add(m);
                }
            }
        }
        return ensemble;
    }
}

