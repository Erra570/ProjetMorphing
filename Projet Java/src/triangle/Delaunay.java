package triangle;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Delaunay {

	private static final Point POINT1 = new Point(350, -1000);
	private static final Point POINT2 = new Point(-750, 750);
	private static final Point POINT3 = new Point(1350, 700);
	private static final Point POINTA = new Point(0,0);
	private static final Point POINTB = new Point(0,500);
	private static final Point POINTC = new Point(500,0);
	private static final Point POINTD = new Point(500,500);
	private ArrayList<Triangle> listeTriangle = new ArrayList<Triangle>();
	private Triangle superTriangle;
	private  ArrayList<Circle> pointGraph1;
	private Color couleur;

	public Delaunay(Pane root1, Pane root2, ArrayList<Circle> pointGraph1, ArrayList<Circle> pointGraph2, Color couleur, boolean modeState,Button del) {
		this.couleur = couleur;
		this.pointGraph1=pointGraph1;
		MouseClickHandlerDelaunay clickHandler = new MouseClickHandlerDelaunay(pointGraph1, root1, pointGraph2, root2, this.couleur, modeState,del);
		root1.setOnMouseClicked(clickHandler);
	}
	
	public ArrayList<Circle> getPGraphe() {
		return this.pointGraph1;
	}
	
	public ArrayList<Triangle> getLTriangle(){
		return this.listeTriangle;
	}
	

		public void initTriang(Pane root) {
			try {
				ArrayList<Point> listePoint = new ArrayList<Point>();
				for (int i = 0; i < pointGraph1.size(); i++) {
					listePoint.add(new Point(pointGraph1.get(i).getCenterX(), pointGraph1.get(i).getCenterY()));
				}
				listePoint.add(POINTA);
				listePoint.add(POINTB);
				listePoint.add(POINTC);
				listePoint.add(POINTD);
				listeTriangle = triangulation(listePoint);
				ArrayList<Line> listeLigne = affichageTriangle(listeTriangle);
				root.getChildren().addAll(listeLigne);
				System.out.println("fini");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void deleteLastPoint(Pane root, Button del) {
			if (!this.getPGraphe().isEmpty()) {
				root.getChildren().remove(this.getPGraphe().size() -1);
				this.getPGraphe().remove(this.getPGraphe().size() - 1);
				if (this.getPGraphe().size() == 0) {
					del.setDisable(true);
				}
			}
		}

	public ArrayList<Triangle> triangulation(ArrayList<Point> listePoint) throws Exception {
		superTriangle = new Triangle(POINT1, POINT2, POINT3);
		listeTriangle.add(superTriangle);
		for (int i = 0; i < listePoint.size(); i++) {
			listeTriangle = ajoutTriangle(listePoint.get(i), listeTriangle);
		}
		listeTriangle = epurationTriangle(listeTriangle);
		return listeTriangle;
	}

	public ArrayList<Triangle> ajoutTriangle(Point point, ArrayList<Triangle> listeTriangle) throws Exception {
		ArrayList<Cote> listeCote = new ArrayList<Cote>();
		ArrayList<Triangle> nouvelleListeTriangle = new ArrayList<Triangle>();

		for (int i = 0; i < listeTriangle.size(); i++) {
			if (listeTriangle.get(i).getCercle().contient(point)) {
				listeCote.add(new Cote(listeTriangle.get(i).getPoint_1(), listeTriangle.get(i).getPoint_2()));
				listeCote.add(new Cote(listeTriangle.get(i).getPoint_2(), listeTriangle.get(i).getPoint_3()));
				listeCote.add(new Cote(listeTriangle.get(i).getPoint_3(), listeTriangle.get(i).getPoint_1()));
			} else {
				nouvelleListeTriangle.add(listeTriangle.get(i));
			}
		}
		listeCote = coteUnique(listeCote);
		for (int k = 0; k < listeCote.size(); k++) {
			nouvelleListeTriangle.add(new Triangle(listeCote.get(k).getDebut(), listeCote.get(k).getFin(), point));
		}
		return nouvelleListeTriangle;
	}

	public ArrayList<Cote> coteUnique(ArrayList<Cote> listeCote) {
		ArrayList<Cote> listeCoteUnique = new ArrayList<Cote>();
		for (int i = 0; i < listeCote.size(); i++) {
			boolean unique = true;
			for (int j = 0; j < listeCote.size(); j++) {
				if (i != j && listeCote.get(i).equals(listeCote.get(j))) {
					unique = false;
					break;
				}

			}
			if (unique) {
				listeCoteUnique.add(listeCote.get(i));
			}
		}
		return listeCoteUnique;
	}

	public ArrayList<Triangle> epurationTriangle(ArrayList<Triangle> listeTriangle) {
		ArrayList<Triangle> nouvelleListeTriangle = new ArrayList<Triangle>();
		for (int i = 0; i < listeTriangle.size(); i++) {
			if (!(listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_1())
					|| listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_2())
					|| listeTriangle.get(i).getPoint_1().equals(superTriangle.getPoint_3())
					|| listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_1())
					|| listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_2())
					|| listeTriangle.get(i).getPoint_2().equals(superTriangle.getPoint_3())
					|| listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_1())
					|| listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_2())
					|| listeTriangle.get(i).getPoint_3().equals(superTriangle.getPoint_3()))) {
				nouvelleListeTriangle.add(listeTriangle.get(i));
			}
		}
		return nouvelleListeTriangle;
	}

	public ArrayList<Line> affichageTriangle(ArrayList<Triangle> listeTriangle) {
		ArrayList<Line> listeLigne = new ArrayList<Line>();
		for (int i = 0; i < listeTriangle.size(); i++) {
			listeLigne.add(new Line(listeTriangle.get(i).getPoint_1().getX(), listeTriangle.get(i).getPoint_1().getY(),
					listeTriangle.get(i).getPoint_2().getX(), listeTriangle.get(i).getPoint_2().getY()));

			listeLigne.add(new Line(listeTriangle.get(i).getPoint_2().getX(), listeTriangle.get(i).getPoint_2().getY(),
					listeTriangle.get(i).getPoint_3().getX(), listeTriangle.get(i).getPoint_3().getY()));

			listeLigne.add(new Line(listeTriangle.get(i).getPoint_3().getX(), listeTriangle.get(i).getPoint_3().getY(),
					listeTriangle.get(i).getPoint_1().getX(), listeTriangle.get(i).getPoint_1().getY()));
		}
		return listeLigne;
	}

}