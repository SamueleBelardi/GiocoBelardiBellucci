package gioco;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GiocoProva extends Application{
	
	// relative al movimento del personaggio
	private Timeline animazioneMovimento;
	private boolean isMoving = false;
	private String direzione = "giu";
	private boolean statoAlternato = false; // serve per alternare fermo/movimento
	
	// bitmap
	Mappa mappaUno = new Mappa("ScenarioUno.txt");
	Mappa mappaDue = new Mappa("ScenarioDue.txt");
	Mappa mappaTre = new Mappa("ScenarioTre.txt");
	Mappa mappaQuattro = new Mappa("ScenarioQuattro.txt");
	Mappa mappaCinque = new Mappa("ScenarioCinque.txt");
	
	// posizione personaggio, dimensioni mappe e personaggio
	double movimento = 2;
	double dimensioneXPersonaggio = 200;
	double dimensioneYPersonaggio = 200;
	static final double DIMENSIONE_X = 320;
	static final double DIMENSIONE_Y = 320;
	static final double DIMENSIONE_X_PERSONAGGIO = 16;
	static final double DIMENSIONE_Y_PERSONAGGIO = 32;
	
	// immagini degli scenari
	Image scenarioUno = new Image(getClass().getResourceAsStream("ScenarioUno.png"));
	Image scenarioDue = new Image(getClass().getResourceAsStream("ScenarioDue.png"));
	Image scenarioTre = new Image(getClass().getResourceAsStream("ScenarioTre.png"));
	Image scenarioQuattro = new Image(getClass().getResourceAsStream("ScenarioQuattro.png"));
	Image scenarioCinque = new Image(getClass().getResourceAsStream("ScenarioCinque.png"));
	
	//immagini personaggio movimento e quando sta fermo
	Image fermoGiu = new Image(getClass().getResourceAsStream("fermoGiu.png"));
	Image movimentoGiu = new Image(getClass().getResourceAsStream("movimentoGiu.png"));
	Image fermoDestra = new Image(getClass().getResourceAsStream("fermoDestra.png"));
	Image movimentoDestra = new Image(getClass().getResourceAsStream("movimentoDestra.png"));
	Image fermoSu = new Image(getClass().getResourceAsStream("fermoSu.png"));
	Image movimentoSu = new Image(getClass().getResourceAsStream("movimentoSu.png"));
	Image fermoSinistra = new Image(getClass().getResourceAsStream("fermoSinistra.png"));
	Image movimentoSinistra = new Image(getClass().getResourceAsStream("movimentoSinistra.png"));
	Image moneta = new Image(getClass().getResourceAsStream("moneta.png"));
	
	ImageView q1 = new ImageView(scenarioUno);
	ImageView q2 = new ImageView(scenarioDue);
	ImageView q3 = new ImageView(scenarioTre);
	ImageView q4 = new ImageView(scenarioQuattro);
	ImageView q5 = new ImageView(scenarioCinque);
	ImageView personaggio1 = new ImageView(fermoGiu);
	ImageView obbiettivo = new ImageView(moneta);
	Pane areaDiGioco = new Pane();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		areaDiGioco.getChildren().add(q1);
		areaDiGioco.getChildren().add(personaggio1);
		personaggio1.setX(dimensioneXPersonaggio);
		personaggio1.setY(dimensioneYPersonaggio);
		
		// animazione
		animazioneMovimento = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
		    if (isMoving) {
		        statoAlternato = !statoAlternato;
		        aggiornaImmaginePersonaggio();
		    }
		}));
		animazioneMovimento.setCycleCount(Timeline.INDEFINITE);
		animazioneMovimento.play();
		
		Scene scena = new Scene(areaDiGioco);
		primaryStage.setTitle("QualitaAria");
		primaryStage.setScene(scena);
		primaryStage.show();
		scena.setOnKeyPressed( e -> tastoPremuto(e));
		scena.setOnKeyReleased(e -> tastoRilasciato(e));
		
	}
	
	public boolean puoMuoversi (double dimensioneX, double dimensioneY) {
		dimensioneX = (dimensioneXPersonaggio / 16) ;
		dimensioneY = (dimensioneYPersonaggio / 16) ;
		boolean puoMuoversi = true;
		
		if (mappaUno.getMappa()[(int) dimensioneX][(int) dimensioneY] == '1') {
			puoMuoversi = false;
		}
		return puoMuoversi;
		
	}

	private void tastoPremuto(KeyEvent e) {
		
		 String tasto = e.getText().toLowerCase();
		    isMoving = true;

		    switch (tasto) {
		        case "w":
		            dimensioneYPersonaggio -= movimento;
		            direzione = "su";
		            break;
		        case "s":
		            dimensioneYPersonaggio += movimento;
		            direzione = "giu";
		            break;
		        case "a":
		            dimensioneXPersonaggio -= movimento;
		            direzione = "sinistra";
		            break;
		        case "d":
		            dimensioneXPersonaggio += movimento;
		            direzione = "destra";
		            break;
		    }

		    aggiornaImmaginePersonaggio();
		
		// controllo collisioni
		if(puoMuoversi(dimensioneXPersonaggio, dimensioneYPersonaggio) == false) {
			System.out.println(isMoving);
			if(e.getText().equals("w") || e.getText().equals("W")) {
				dimensioneYPersonaggio += 10;
			}
			if(e.getText().equals("s") || e.getText().equals("S")) {
				dimensioneYPersonaggio -= 10;
			}
			if(e.getText().equals("a") || e.getText().equals("A")) {
				dimensioneXPersonaggio += 10;
			}
			if(e.getText().equals("d") || e.getText().equals("D")) {
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
	
	private void tastoRilasciato(KeyEvent e) {
		 isMoving = false;
		    statoAlternato = false;
		    aggiornaImmaginePersonaggio();
		    animazioneMovimento.stop();
	}
	
	
	
	private void aggiornaImmaginePersonaggio() {
	    switch (direzione) {
	        case "giu":
	            personaggio1.setImage(statoAlternato && isMoving ? movimentoGiu : fermoGiu);
	            break;
	        case "su":
	            personaggio1.setImage(statoAlternato && isMoving ? movimentoSu : fermoSu);
	            break;
	        case "sinistra":
	            personaggio1.setImage(statoAlternato && isMoving ? movimentoSinistra : fermoSinistra);
	            break;
	        case "destra":
	            personaggio1.setImage(statoAlternato && isMoving ? movimentoDestra : fermoDestra);
	            break;
	    }
	}

	public static void main(String[] args) {
		launch(args);
	}
}
