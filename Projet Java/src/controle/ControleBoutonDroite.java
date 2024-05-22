package controle;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import presentation.Fichier;

@SuppressWarnings("deprecation")
public class ControleBoutonDroite implements Observer, EventHandler<ActionEvent>{
	
	private Album alb;
	private Fichier f;
	
	public Fichier getF() {
		return f;
	}

	public ControleBoutonDroite(Album alb, Fichier f) {
		this.alb = alb;
		this.f = f;
	}

	@Override
	public void handle(ActionEvent arg0) {
		FileChooser fc = new FileChooser();
		final File file = fc.showOpenDialog(null); 
		alb.setImageFin(file.toURI().toString());
		f.setF(file);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
