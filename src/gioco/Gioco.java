package gioco;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gioco extends Application{

	/*
	 * Hai da fa ste cose stronzo (il più possibile) se non capisci qualcosa ho spiegato tutto
	 * TODO animazione movimento del personaggio
	 * TODO mettere sulla mappa monete da raccogliere "obbiettivo del gioco" che quando ci passa sopra si levano dalla mappa
	 */

	// bitmap, servono per vedere se il personaggio puo camminare o no su un determinato riquadro
	Mappa mappaUno = new Mappa("ScenarioUno.txt");
	Mappa mappaDue = new Mappa("ScenarioDue.txt");
	Mappa mappaTre = new Mappa("ScenarioTre.txt");
	Mappa mappaQuattro = new Mappa("ScenarioQuattro.txt");
	Mappa mappaCinque = new Mappa("ScenarioCinque.txt");
	Mappa mappaSelezionata = mappaUno;

	double movimento = 3; 
	double posizioneXPersonaggio = 180; // posizione X personaggio nella mappa
	double posizioneYPersonaggio = 180; // posizione Y personaggio nella mappa
	static final double DIMENSIONE_X = 320; // dimensione X della mappa
	static final double DIMENSIONE_Y = 320; // dimensione Y della mappa
	static final double DIMENSIONE_X_PERSONAGGIO = 16; // numero di pixel X (grandezza) del personaggio
	static final double DIMENSIONE_Y_PERSONAGGIO = 32; // numero di pixel Y (grandezza) del personaggio

	// hitbox per cambio mappa
	Rectangle hitBoxMappaUnoToDue = new Rectangle(16, 16);
	Rectangle  hitBoxMappaDueToUno = new Rectangle(16, 16);
	Rectangle hitBoxMappaDueToTre = new Rectangle(48, 16);
	Rectangle hitBoxMappaTreToDue = new Rectangle(16, 16);
	Rectangle  hitBoxMappaTreToQuattro = new Rectangle(16, 48);
	Rectangle  hitBoxMappaTreToCinque = new Rectangle(48, 16);
	Rectangle  hitBoxMappaQuattroToTre = new Rectangle(16,32);
	Rectangle  hitBoxMappaCinqueToTre = new Rectangle(48, 16); 

	// immagini degli scenari
	Image scenarioUno = new Image(getClass().getResourceAsStream("ScenarioUno.png"));
	Image scenarioDue = new Image(getClass().getResourceAsStream("ScenarioDue.png"));
	Image scenarioTre = new Image(getClass().getResourceAsStream("ScenarioTre.png"));
	Image scenarioQuattro = new Image(getClass().getResourceAsStream("ScenarioQuattro.png"));
	Image scenarioCinque = new Image(getClass().getResourceAsStream("ScenarioCinque.png"));

	// immagini del personaggio quando è in movimento o fermo
	Image fermoGiu = new Image(getClass().getResourceAsStream("fermoGiu.png"));
	Image movimentoGiu = new Image(getClass().getResourceAsStream("movimentoGiu.png"));
	Image fermoDestra = new Image(getClass().getResourceAsStream("fermoDestra.png"));
	Image movimentoDestra = new Image(getClass().getResourceAsStream("movimentoDestra.png"));
	Image fermoSu = new Image(getClass().getResourceAsStream("fermoSu.png"));
	Image movimentoSu = new Image(getClass().getResourceAsStream("movimentoSu.png"));
	Image fermoSinistra = new Image(getClass().getResourceAsStream("fermoSinistra.png"));
	Image movimentoSinistra = new Image(getClass().getResourceAsStream("movimentoSinistra.png"));
	Image moneta = new Image(getClass().getResourceAsStream("moneta.png"));

	//vettori di immagini
	Image [] sequenzaGiu = new Image[1000];
	Image [] sequenzaDestra = new Image[1000];
	Image [] sequenzaSu = new Image[1000];
	Image [] sequenzaSinistra = new Image[1000];
	int contatoreGiu = 0;
	int contatoreDestra = 0;
	int contatoreSu = 0;
	int contatoreSinistra = 0;

	ImageView q1 = new ImageView(scenarioUno);
	ImageView q2 = new ImageView(scenarioDue);
	ImageView q3 = new ImageView(scenarioTre);
	ImageView q4 = new ImageView(scenarioQuattro);
	ImageView q5 = new ImageView(scenarioCinque);
	ImageView personaggio1 = new ImageView(fermoGiu);
	ImageView obbiettivo = new ImageView(moneta);

	// Variabile per capire la direzione in cui sta andando il personaggio
	String movimentoAttuale = "";

	// pane in cui si aggiungono gli scenari, personaggio, ecc
	Pane areaDiGioco = new Pane();

	@Override
	public void start(Stage primaryStage) throws Exception {

		// aggiunta degli elementi al pane
		areaDiGioco.getChildren().add(q1);
		areaDiGioco.getChildren().add(personaggio1);
		personaggio1.setX(posizioneXPersonaggio);
		personaggio1.setY(posizioneYPersonaggio);
		areaDiGioco.getChildren().add(hitBoxMappaUnoToDue);
		hitBoxMappaUnoToDue.setX(192);
		hitBoxMappaUnoToDue.setY(304);
		hitBoxMappaUnoToDue.setFill(Color.RED);

		// for che inserisce nei vettori le immagini in movimento e fermo
		for(int i = 0; i < 1; i++) {
			if(i%2 == 0) {
				sequenzaGiu [i] = new Image(getClass().getResourceAsStream("movimentoGiu.png"));
				sequenzaDestra [i] = new Image(getClass().getResourceAsStream("movimentoDestra.png"));
				sequenzaSu [i] = new Image(getClass().getResourceAsStream("movimentoSu.png"));
				sequenzaSinistra [i] = new Image(getClass().getResourceAsStream("movimentoSinistra.png"));
			} else {
				sequenzaGiu [i] = new Image(getClass().getResourceAsStream("fermoGiu.png"));
				sequenzaDestra [i] = new Image(getClass().getResourceAsStream("fermoDestra.png"));
				sequenzaSu [i] = new Image(getClass().getResourceAsStream("fermoSu.png"));
				sequenzaSinistra [i] = new Image(getClass().getResourceAsStream("fermoSinistra.png"));
			}
		}

		// timeline che permette il movimento animato del personaggio
		Timeline timeline = new Timeline(new KeyFrame(
				Duration.seconds(0.032), 
				x -> aggiornaMovimento()));
		timeline.setCycleCount(-1);
		timeline.play();

		Scene scena = new Scene(areaDiGioco);
		primaryStage.setTitle("Gioco");
		primaryStage.setScene(scena);
		primaryStage.show();
		scena.setOnKeyPressed( e -> tastoPremuto(e));
		scena.setOnKeyReleased(e -> tastoRilasciato(e));
	}

	// metodo che quando rilascio il tasto imposta l'immagine del personaggio a fermo
	private void tastoRilasciato(KeyEvent e) {
		String tasto = e.getText().toLowerCase();
		movimentoAttuale = ""; // Svuota il movimento attuale per fermare l'animazione

		// switch che imposta l'immagine
		switch (tasto) {
		case "w":
			personaggio1.setImage(fermoSu);
			break;
		case "s":
			personaggio1.setImage(fermoGiu);
			break;
		case "a":
			personaggio1.setImage(fermoSinistra);
			break;
		case "d":
			personaggio1.setImage(fermoDestra);
			break;
		}
	}

	//  metodo che permette il movimento fluido
	private void aggiornaMovimento() {

		// controlla se è presente qualcosa nella variabile
		if (!movimentoAttuale.isEmpty()) {
			switch (movimentoAttuale) { // in base alla lettera aggiorna l'immagine
			case "w":
				personaggio1.setImage(sequenzaSu[contatoreSu % 2]);
				contatoreSu++; // aumenta il contatore in modo tale che il giro successivo cambia con un'altra immagine
				break;
			case "s":
				personaggio1.setImage(sequenzaGiu[contatoreGiu % 2]);
				contatoreGiu++; // aumenta il contatore in modo tale che il giro successivo cambia con un'altra immagine
				break;
			case "a":
				personaggio1.setImage(sequenzaSinistra[contatoreSinistra % 2]);
				contatoreSinistra++; // aumenta il contatore in modo tale che il giro successivo cambia con un'altra immagine
				break;
			case "d":
				personaggio1.setImage(sequenzaDestra[contatoreDestra % 2]);
				contatoreDestra++; // aumenta il contatore in modo tale che il giro successivo cambia con un'altra immagine
				break;
			}
		}	
	}

	// metodo che fa muovore il personaggio quando si preme un determinato tasto
	private void tastoPremuto(KeyEvent e) {
		// servono per reimpostara la posizione iniziale in caso di collisone
		double nuovaX = posizioneXPersonaggio;
		double nuovaY = posizioneYPersonaggio;

		// Ottieni il tasto premuto e imposta il movimento attuale
		String tasto = e.getText().toLowerCase();
		movimentoAttuale = tasto;

		switch (tasto) {
		case "w":
			posizioneYPersonaggio -= movimento;
			break;
		case "s":
			posizioneYPersonaggio += movimento;
			break;
		case "a":
			posizioneXPersonaggio -= movimento;
			break;
		case "d":
			posizioneXPersonaggio += movimento;
			break;
		}

		// controllo collisioni
		if(puoMuoversi(nuovaX, nuovaY)) {
			posizioneXPersonaggio = nuovaX;
			posizioneYPersonaggio = nuovaY;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// controllo che il personaggio non esce dai bordi
		if(posizioneYPersonaggio>DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO) posizioneYPersonaggio = DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO;
		if(posizioneXPersonaggio>DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO) posizioneXPersonaggio = DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO;
		if(posizioneYPersonaggio<0) posizioneYPersonaggio = 0;
		if(posizioneXPersonaggio<0) posizioneXPersonaggio = 0;

		// reimposto la posizione del personaggio dopo il movimento
		personaggio1.setX(posizioneXPersonaggio);
		personaggio1.setY(posizioneYPersonaggio);

		cambioMappa();
	}

	// metodo che controlla dalla bitmap se il personaggio puo stare su quella mattonella
	public boolean puoMuoversi (double dimensioneX, double dimensioneY) {
		// calcolo posizione del personaggio nella bitmap
		int colonna = (int)(posizioneXPersonaggio / 16);
		int riga = (int)(posizioneYPersonaggio / 16)+1 ; // quel +1 sta li perché senno non funziona non so manco io perche serve

		// assegno ad una variabile il valore presenta in quella detereminata posizione della bitmap
		char cella = mappaSelezionata.getMappa()[riga][colonna];
		System.out.println("Cella [" + riga + "]" + "[" + colonna + "]" + cella);
		return cella == '1'; // ritorno true se cella è uguale a 1
	}

	public void cambioMappa () {

		// boundingbox per controllo collisioni
		Bounds boundPersonaggio = personaggio1.getBoundsInParent();
		Bounds boundMappaUnoToDue = hitBoxMappaUnoToDue.getBoundsInParent();
		Bounds boundMappaDueToUno = hitBoxMappaDueToUno.getBoundsInParent();
		Bounds boundMappaDueToTre = hitBoxMappaDueToTre.getBoundsInParent();
		Bounds boundMappaTreToDue = hitBoxMappaTreToDue.getBoundsInParent();
		Bounds boundMappaTreToQuattro = hitBoxMappaTreToQuattro.getBoundsInParent();
		Bounds boundMappaTreToCinque = hitBoxMappaTreToCinque.getBoundsInParent();
		Bounds boundMappaQuattroToTre = hitBoxMappaQuattroToTre.getBoundsInParent();
		Bounds boundMappaCinqueToTre = hitBoxMappaCinqueToTre.getBoundsInParent();	

		// Da mappaUno a mappaDue
		if (boundPersonaggio.intersects(boundMappaUnoToDue)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q2, personaggio1, hitBoxMappaDueToUno, hitBoxMappaDueToTre);

			// Posizione hitbox per il ritorno a mappaUno
			hitBoxMappaDueToUno.setX(192);
			hitBoxMappaDueToUno.setY(0);
			hitBoxMappaDueToUno.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappaTre
			hitBoxMappaDueToTre.setX(16);
			hitBoxMappaDueToTre.setY(304);
			hitBoxMappaDueToTre.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaDue;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 192;
			posizioneYPersonaggio = 17;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaDue a mappaUno
		if (boundPersonaggio.intersects(boundMappaDueToUno)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q1, personaggio1, hitBoxMappaUnoToDue);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaUnoToDue.setX(192);
			hitBoxMappaUnoToDue.setY(304);
			hitBoxMappaUnoToDue.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaUno;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 192;
			posizioneYPersonaggio = 270;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaDue a mappaTre
		if (boundPersonaggio.intersects(boundMappaDueToTre) ) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q3, personaggio1, hitBoxMappaTreToDue,
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(304);
			hitBoxMappaTreToDue.setY(0);
			hitBoxMappaTreToDue.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(0);
			hitBoxMappaTreToQuattro.setY(140);
			hitBoxMappaTreToQuattro.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(96);
			hitBoxMappaTreToCinque.setY(304);
			hitBoxMappaTreToCinque.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaTre;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 288;
			posizioneYPersonaggio = 16;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaTre a mappaDue
		if(boundPersonaggio.intersects(boundMappaTreToDue)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q2, personaggio1, hitBoxMappaDueToUno, hitBoxMappaDueToTre);

			// Posizione hitbox per il ritorno a mappaUno
			hitBoxMappaDueToUno.setX(192);
			hitBoxMappaDueToUno.setY(0);
			hitBoxMappaDueToUno.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappaTre
			hitBoxMappaDueToTre.setX(16);
			hitBoxMappaDueToTre.setY(304);
			hitBoxMappaDueToTre.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaDue;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 32;
			posizioneYPersonaggio = 272;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaTre a mappaQuattro
		if(boundPersonaggio.intersects(boundMappaTreToQuattro)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q4, personaggio1, hitBoxMappaQuattroToTre);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaQuattroToTre.setX(304);
			hitBoxMappaQuattroToTre.setY(128);
			hitBoxMappaQuattroToTre.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaQuattro;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 288;
			posizioneYPersonaggio = 128;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaQuattro a mappaTre
		if (boundPersonaggio.intersects(boundMappaQuattroToTre) ) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q3, personaggio1, hitBoxMappaTreToDue,
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(304);
			hitBoxMappaTreToDue.setY(0);
			hitBoxMappaTreToDue.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(0);
			hitBoxMappaTreToQuattro.setY(140);
			hitBoxMappaTreToQuattro.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(96);
			hitBoxMappaTreToCinque.setY(304);
			hitBoxMappaTreToCinque.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaTre;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 16;
			posizioneYPersonaggio = 140;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaTre a mappaCinque
		if(boundPersonaggio.intersects(boundMappaTreToCinque)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q5, personaggio1, hitBoxMappaCinqueToTre);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaCinqueToTre.setX(112);
			hitBoxMappaCinqueToTre.setY(0);
			hitBoxMappaCinqueToTre.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaCinque;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 128;
			posizioneYPersonaggio = 16;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}

		// Da mappaCinque a mappaTre
		if(boundPersonaggio.intersects(boundMappaCinqueToTre)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q3, personaggio1, hitBoxMappaTreToDue,
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(304);
			hitBoxMappaTreToDue.setY(0);
			hitBoxMappaTreToDue.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(0);
			hitBoxMappaTreToQuattro.setY(140);
			hitBoxMappaTreToQuattro.setFill(Color.RED);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(96);
			hitBoxMappaTreToCinque.setY(304);
			hitBoxMappaTreToCinque.setFill(Color.RED);

			// Cambio la bitmap
			mappaSelezionata = mappaTre;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 112;
			posizioneYPersonaggio = 272;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}
	}



	public static void main(String[] args) {
		launch(args);
	}

	public void mostra(Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}
}
