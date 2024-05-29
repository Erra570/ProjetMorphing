package morphingFonction;

public class Pile {

	private PointMorphing[] tab;
	private int courant;
	
	public Pile() { 
		this.tab = new PointMorphing[500*500];
		courant = -1;
	}
	
	public void empiler(int x, int y) {
		courant++;
		this.tab[courant] =  new PointMorphing(x,y);
	}

	public void depiler() {
		if (courant > -1)
			courant--;
	}

	public PointMorphing sommet() {
		return (this.vide()) ?	null : this.tab[courant];
	}

	public boolean vide() {
		return (this.courant == -1);
	}

}
