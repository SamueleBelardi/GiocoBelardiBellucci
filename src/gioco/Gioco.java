package gioco;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Gioco extends Application{
	
	Personaggio personaggioUno;
	ImageView mappaUno;
	ImageView pUno;
	Image mappa = new Image(getClass().getResourceAsStream("ScenarioUno.png"));
	Image protagonista = new Image(getClass().getResourceAsStream("ScenarioUno.png"));

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GridPane g = new GridPane();
		mappaUno = new ImageView(mappa);
		g.add(mappaUno, 0, 0);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
