package triangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Delaunay extends Application {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	

	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}


	public static void main(String[] args) {
		launch(args);
	}
}
