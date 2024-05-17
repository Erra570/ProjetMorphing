package abstraction;

import java.io.File;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import presentation.Fichier;

@SuppressWarnings("deprecation")
public class Album extends Observable {

	@SuppressWarnings("removal")
	public static final Integer IMAGE_Depart = new Integer(0); // PAC
	@SuppressWarnings("removal")
	public static final Integer IMAGE_Fin = new Integer(1); // PAC
	
	private Image imageDepart;
	private Image imageFin;
	private Fichier f;

	public Album(Fichier f) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(null);
		this.imageDepart = new Image(file.toURI().toString());
		this.f = f;
		f.setF(file);
		
		fc = new FileChooser();
		file = fc.showOpenDialog(null);
		this.imageFin = new Image(file.toURI().toString());
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