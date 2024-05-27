package triangle;

public class Cote {
	private Point debut;
	private Point fin;

	public Cote(Point debut, Point fin) {
		this.debut = debut;
		this.fin = fin;
	}

	public Point getDebut() {
		return debut;
	}

	public Point getFin() {
		return fin;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cote) {
			Cote c2 = (Cote) obj;
			if ((this.getDebut().equals(c2.getDebut()) && this.getFin().equals(c2.getFin()))
					|| (this.getDebut().equals(c2.getFin()) && this.getFin().equals(c2.getDebut()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Cote [debut=" + debut + ", fin=" + fin + "]";
	}
	
	
}
