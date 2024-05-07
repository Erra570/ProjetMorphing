package controle;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ControleBoutonDroite implements Observer, EventHandler<ActionEvent>{
	
	private Album alb;
	
	public ControleBoutonDroite(Album alb) {
		this.alb = alb;
	}

	@Override
	public void handle(ActionEvent arg0) {
		FileChooser fc = new FileChooser();
		final File file = fc.showOpenDialog(null); 
		alb.setImageFin(file.toURI().toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
