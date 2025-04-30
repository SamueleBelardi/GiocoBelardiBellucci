package gioco;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Gioco extends Application{
	
	Mappa mappaUno = new Mappa("ScenarioUno.txt");
	Mappa mappaDue = new Mappa("ScenarioDue.txt");
	Mappa mappaTre = new Mappa("ScenarioTre.txt");
	Mappa mappaQuattro = new Mappa("ScenarioQuattro.txt");
	Mappa mappaCinque = new Mappa("ScenarioCinque.txt");
	double dimensioneXPersonaggio = 200;
	double dimensioneYPersonaggio = 200;
	static final double DIMENSIONE_X = 320;
	static final double DIMENSIONE_Y = 320;
	static final double DIMENSIONE_X_PERSONAGGIO = 16;
	static final double DIMENSIONE_Y_PERSONAGGIO = 32;
	Image scenarioUno = new Image(getClass().getResourceAsStream("ScenarioUno.png"));
	Image scenarioDue = new Image(getClass().getResourceAsStream("ScenarioDue.png"));
	Image scenarioTre = new Image(getClass().getResourceAsStream("ScenarioTre.png"));
	Image scenarioQuattro = new Image(getClass().getResourceAsStream("ScenarioQuattro.png"));
	Image scenarioCinque = new Image(getClass().getResourceAsStream("ScenarioCinque.png"));
	Image protagonista = new Image(getClass().getResourceAsStream("protagonista.png"));
	Image moneta = new Image(getClass().getResourceAsStream("moneta.png"));
	ImageView q1 = new ImageView(scenarioUno);
	ImageView q2 = new ImageView(scenarioDue);
	ImageView q3 = new ImageView(scenarioTre);
	ImageView q4 = new ImageView(scenarioQuattro);
	ImageView q5 = new ImageView(scenarioCinque);
	ImageView personaggio1 = new ImageView(protagonista);
	ImageView obbiettivo = new ImageView(moneta);
	Pane areaDiGioco = new Pane();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		areaDiGioco.getChildren().add(q1);
		areaDiGioco.getChildren().add(personaggio1);
		personaggio1.setX(dimensioneXPersonaggio);
		personaggio1.setY(dimensioneYPersonaggio);
		
		Scene scena = new Scene(areaDiGioco);
		primaryStage.setTitle("QualitaAria");
		primaryStage.setScene(scena);
		primaryStage.show();
		scena.setOnKeyPressed( e -> tastoPremuto(e));
		
	}
	
	public boolean puoMuoversi (double dimensioneX, double dimensioneY) {
		dimensioneX = (dimensioneXPersonaggio / 16) - 1;
		dimensioneY = (dimensioneYPersonaggio / 16) - 1;
		boolean puoMuoversi = true;
		
		if (mappaUno.getMappa()[(int) dimensioneX][(int) dimensioneY] == '1') {
			puoMuoversi = false;
		}
		return puoMuoversi;
		
	}

	private void tastoPremuto(KeyEvent e) {
		
		// movimento personaggio
		if(e.getText().equals("w") || e.getText().equals("W")) {
			dimensioneYPersonaggio -= 10;
		}
		if(e.getText().equals("s") || e.getText().equals("S")) {
			dimensioneYPersonaggio += 10;
		}
		if(e.getText().equals("d") || e.getText().equals("D")) {
			dimensioneXPersonaggio -= 10;
		}
		if(e.getText().equals("a") || e.getText().equals("A")) {
			dimensioneXPersonaggio += 10;
		}
		
		// controllo collisioni
		if(puoMuoversi(dimensioneXPersonaggio, dimensioneYPersonaggio) == false) {
			if(e.getText().equals("w") || e.getText().equals("W")) {
				dimensioneYPersonaggio += 10;
			}
			if(e.getText().equals("s") || e.getText().equals("S")) {
				dimensioneYPersonaggio -= 10;
			}
			if(e.getText().equals("d") || e.getText().equals("D")) {
				dimensioneXPersonaggio += 10;
			}
			if(e.getText().equals("a") || e.getText().equals("A")) {
				dimensioneXPersonaggio -= 10;
			}
		}
		// controllo che il personaggio non esce dai bordi
		if(dimensioneYPersonaggio>DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO) {
			dimensioneYPersonaggio = DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO;
		}
		if(dimensioneXPersonaggio>DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO) {
			dimensioneXPersonaggio = DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO;
		}
		if(dimensioneYPersonaggio<0) {
			dimensioneYPersonaggio = 0;
		}
		if(dimensioneXPersonaggio<0) {
			dimensioneXPersonaggio = 0;
		}
		
		personaggio1.setX(dimensioneXPersonaggio);
		personaggio1.setY(dimensioneYPersonaggio);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
