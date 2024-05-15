package controle;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import presentation.Fichier;

@SuppressWarnings("deprecation")
public class ControleBoutonGauche implements Observer, EventHandler<ActionEvent>{
	
	private Album alb;
	private Fichier f;
	
	public ControleBoutonGauche(Album alb, Fichier f) {
		this.alb = alb;
		this.f = f;
	}

	@Override
	public void handle(ActionEvent arg0) {
		FileChooser fc = new FileChooser();
		final File file = fc.showOpenDialog(null); 
		alb.setImageDepart(file.toURI().toString());
		f.setF(file);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
