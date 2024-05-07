package abstraction;

import java.util.Observable;

import javafx.scene.image.Image;

public class Album extends Observable {

	public static final Integer IMAGE_Depart = new Integer(0); // PAC
	public static final Integer IMAGE_Fin = new Integer(1); // PAC
	
	private Image imageDepart;
	private Image imageFin;

	public Album(Image img) {
		this.imageDepart = img;
		this.imageFin = img;
	}

	public void setImageDepart(String fullpathname) {
		this.imageDepart = new Image(fullpathname);
		this.setChanged(); // PAC
		this.notifyObservers(IMAGE_Depart); // PAC
	}
	
	public void setImageFin(String fullpathname) {
		this.imageFin = new Image(fullpathname);
		this.setChanged(); // PAC
		this.notifyObservers(IMAGE_Fin); // PAC
	}
	
	public Image getImageDepart() {
		return(this.imageDepart);
	}
	
	public Image getImageFin() {
		return(this.imageFin);
	}
}