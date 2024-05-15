package controle;

import java.util.Observable;
import java.util.Observer;

import abstraction.Album;
import javafx.scene.image.ImageView;

public class ControleImageFin implements Observer{
	
	private Album alb;
	private ImageView image;
	
	public ControleImageFin(Album alb, ImageView image) {
		this.alb = alb;
		this.image = image;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(((Integer) arg).intValue() == 1)
		{
			image.setImage(alb.getImageFin());	
		}
	}
}
