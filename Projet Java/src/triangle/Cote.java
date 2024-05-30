package triangle;

/**
 * La classe Cote représente un côté d'un triangle défini par deux points.
 */
public class Cote {
	
	private Point debut; // Point de départ du côté
	private Point fin; // Point d'arrivée du côté

	/**
	 * Constructeur de la classe Cote.
	 * @param debut Le point de départ du côté.
	 * @param fin Le point d'arrivée du côté.
	 */
	public Cote(Point debut, Point fin) {
		this.debut = debut;
		this.fin = fin;
	}

	/**
	 * Obtient le point de départ du côté.
	 * @return Le point de départ du côté.
	 */
	public Point getDebut() {
		return debut;
	}

	/**
	 * Obtient le point d'arrivée du côté.
	 * @return Le point d'arrivée du côté.
	 */
	public Point getFin() {
		return fin;
	}

	/**
	 * Vérifie si deux côtés sont égaux.
	 * @param obj L'objet à comparer avec le côté actuel.
	 * @return true si les côtés sont égaux, sinon false.
	 */
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

	/**
	 * Renvoie une représentation sous forme de chaîne de caractères du côté.
	 * @return Une chaîne de caractères représentant le côté.
	 */
	@Override
	public String toString() {
		return "Cote [debut=" + debut + ", fin=" + fin + "]";
	}
}
