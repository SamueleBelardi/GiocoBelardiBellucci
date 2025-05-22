package gioco;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Gioco extends BorderPane{
	
	
	PrimaPagina p;
	public Gioco (PrimaPagina p) {
		super();
		this.p = p;
	}

	// bitmap, servono per vedere se il personaggio puo camminare o no su un determinato riquadro
	Mappa mappaUno = new Mappa("ScenarioUno.txt");
	Mappa mappaDue = new Mappa("ScenarioDue.txt");
	Mappa mappaTre = new Mappa("ScenarioTre.txt");
	Mappa mappaQuattro = new Mappa("ScenarioQuattro.txt");
	Mappa mappaCinque = new Mappa("ScenarioCinque.txt");
	Mappa mappaSelezionata = mappaUno;

	double movimento = 2; // unita dello spostamento del personaggio 
	double posizioneXPersonaggio = 180; // posizione X personaggio nella mappa
	double posizioneYPersonaggio = 180; // posizione Y personaggio nella mappa
	static final double DIMENSIONE_X = 320; // dimensione X della mappa
	static final double DIMENSIONE_Y = 320; // dimensione Y della mappa
	static final double DIMENSIONE_X_PERSONAGGIO = 16; // numero di pixel X (grandezza) del personaggio
	static final double DIMENSIONE_Y_PERSONAGGIO = 32; // numero di pixel Y (grandezza) del personaggio
	static final double DIMENSIONE_X_MAPPA = 320; 
	static final double DIMENSIONE_Y_MAPPA = 320; 

	Rectangle hitBoxMappaUnoToDue = new Rectangle(16, 16);
	Rectangle  hitBoxMappaDueToUno = new Rectangle(16, 16);
	Rectangle hitBoxMappaDueToTre = new Rectangle(48, 16);
	Rectangle hitBoxMappaTreToDue = new Rectangle(16, 16);
	Rectangle  hitBoxMappaTreToQuattro = new Rectangle(16, 48);
	Rectangle  hitBoxMappaTreToCinque = new Rectangle(48, 16);
	Rectangle  hitBoxMappaQuattroToTre = new Rectangle(16,32);
	Rectangle  hitBoxMappaCinqueToTre = new Rectangle(48, 16); 
	Rectangle  hitBoxObbiettivoUno = new Rectangle(16, 16); 
	Rectangle  hitBoxObbiettivoDue = new Rectangle(16, 16); 
	Rectangle  hitBoxObbiettivoTre = new Rectangle(16, 16); 
	Rectangle  hitBoxObbiettivoQuattro = new Rectangle(16, 16); 
	Rectangle  hitBoxObbiettivoCinque = new Rectangle(16, 16);  
	
	static final double POSIZIONE_HITBOX_UNO_TO_DUE_X = 192;
	static final double POSIZIONE_HITBOX_DUE_TO_UNO_X = 192;
	static final double POSIZIONE_HITBOX_DUE_TO_TRE_X = 16;
	static final double POSIZIONE_HITBOX_TRE_TO_DUE_X = 304;
	static final double POSIZIONE_HITBOX_TRE_TO_QUATTRO_X = 0;
	static final double POSIZIONE_HITBOX_TRE_TO_CINQUE_X = 96;
	static final double POSIZIONE_HITBOX_QUATTRO_TO_TRE_X = 304;
	static final double POSIZIONE_HITBOX_CINQUE_TO_TRE_X = 112;
	static final double POSIZIONE_HITBOX_UNO_TO_DUE_Y = 304;
	static final double POSIZIONE_HITBOX_DUE_TO_UNO_Y = 0;
	static final double POSIZIONE_HITBOX_DUE_TO_TRE_Y = 304;
	static final double POSIZIONE_HITBOX_TRE_TO_DUE_Y = 0;
	static final double POSIZIONE_HITBOX_TRE_TO_QUATTRO_Y = 140;
	static final double POSIZIONE_HITBOX_TRE_TO_CINQUE_Y = 304;
	static final double POSIZIONE_HITBOX_QUATTRO_TO_TRE_Y = 128;
	static final double POSIZIONE_HITBOX_CINQUE_TO_TRE_Y = 0;
	static final double POSIZIONE_MONETA_1_X = 90;
	static final double POSIZIONE_MONETA_2_X = 20;
	static final double POSIZIONE_MONETA_3_X = 140;
	static final double POSIZIONE_MONETA_4_X = 50;
	static final double POSIZIONE_MONETA_5_X = 60;
	static final double POSIZIONE_MONETA_1_Y = 100;
	static final double POSIZIONE_MONETA_2_Y= 120;
	static final double POSIZIONE_MONETA_3_Y = 150;
	static final double POSIZIONE_MONETA_4_Y = 130;
	static final double POSIZIONE_MONETA_5_Y = 240;
		
	// vettore che controlla se una moneta e stata mangiata
	boolean [] monetaPresente = { true, true, true, true, true} ;

	Image scenarioUno = new Image(getClass().getResourceAsStream("ScenarioUno.png"));
	Image scenarioDue = new Image(getClass().getResourceAsStream("ScenarioDue.png"));
	Image scenarioTre = new Image(getClass().getResourceAsStream("ScenarioTre.png"));
	Image scenarioQuattro = new Image(getClass().getResourceAsStream("ScenarioQuattro.png"));
	Image scenarioCinque = new Image(getClass().getResourceAsStream("ScenarioCinque.png"));
	Image fermoGiu = new Image(getClass().getResourceAsStream("fermoGiu.png"));
	Image movimentoGiu = new Image(getClass().getResourceAsStream("movimentoGiu.png"));
	Image fermoDestra = new Image(getClass().getResourceAsStream("fermoDestra.png"));
	Image movimentoDestra = new Image(getClass().getResourceAsStream("movimentoDestra.png"));
	Image fermoSu = new Image(getClass().getResourceAsStream("fermoSu.png"));
	Image movimentoSu = new Image(getClass().getResourceAsStream("movimentoSu.png"));
	Image fermoSinistra = new Image(getClass().getResourceAsStream("fermoSinistra.png"));
	Image movimentoSinistra = new Image(getClass().getResourceAsStream("movimentoSinistra.png"));
	Image moneta = new Image(getClass().getResourceAsStream("moneta.gif"));

	//vettori di immagini per permettere l'animazione
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
	ImageView moneta1 = new ImageView(moneta);
	ImageView moneta2 = new ImageView(moneta);
	ImageView moneta3 = new ImageView(moneta);
	ImageView moneta4 = new ImageView(moneta);
	ImageView moneta5 = new ImageView(moneta);
	
	String movimentoAttuale = ""; // Variabile per capire la direzione in cui sta andando il personaggio
	int moneteRaccolte = 0; // condizione vittoria
	Pane areaDiGioco = new Pane(); 
	Label eMoneteRaccolte = new Label("monete raccolte: "+moneteRaccolte);
	
	

	public void inizio() {

		areaDiGioco.getChildren().add(q1);
		areaDiGioco.getChildren().add(personaggio1);
		personaggio1.setX(posizioneXPersonaggio);
		personaggio1.setY(posizioneYPersonaggio);
		areaDiGioco.getChildren().add(hitBoxMappaUnoToDue);
		hitBoxMappaUnoToDue.setX(192);
		hitBoxMappaUnoToDue.setY(304);
		hitBoxMappaUnoToDue.setFill(Color.TRANSPARENT);
		areaDiGioco.getChildren().add(moneta1);
		moneta1.setX(POSIZIONE_MONETA_1_X);
		moneta1.setY(POSIZIONE_MONETA_1_Y);
		areaDiGioco.getChildren().add(eMoneteRaccolte);
		eMoneteRaccolte.setPrefSize(320, 16);
		eMoneteRaccolte.setTranslateY(320);

		// for che inserisce nei vettori le immagini in movimento e fermo
		for(int i = 0; i < sequenzaSu.length; i++) {
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
				Duration.seconds(0.064), 
				x -> aggiornaMovimento()));
		timeline.setCycleCount(-1);
		timeline.play();
		this.setCenter(areaDiGioco);
	}
	
	public void vittoria() {
		if(moneteRaccolte == 5) {
			
			Timeline timelineFineGioco = new Timeline(new KeyFrame(
					Duration.seconds(10), 
					x -> timer()));
			timelineFineGioco.setCycleCount(1);
			timelineFineGioco.play();
		}
	}

	// metodo che quando finisce il timer apre la schermata finale del gioco
	private void timer() {
		System.out.println("hai finito il gioco complimenti");
		p.fineGioco();
	}

	// metodo che quando rilascio il tasto imposta l'immagine del personaggio a fermo
	public void tastoRilasciato(KeyEvent e) {
		String tasto = e.getText().toLowerCase();
		movimentoAttuale = ""; // Svuota il movimento attuale per fermare l'animazione

		// switch che imposta l'immagine
		switch (tasto) {
		 case "w" -> personaggio1.setImage(fermoSu);
		    case "s" -> personaggio1.setImage(fermoGiu);
		    case "a" -> personaggio1.setImage(fermoSinistra);
		    case "d" -> personaggio1.setImage(fermoDestra);
		}
	}

	//  metodo che permette il movimento fluido
	private void aggiornaMovimento() {

		// controlla se è presente qualcosa quindi se il personaggio e fermo o in movimento
		if (!movimentoAttuale.isEmpty()) {
		    switch (movimentoAttuale) { // alterna l'immagine in movimento o fermo in movimento grazie al calcolo del numero se e pari o dispari
		        case "w" -> personaggio1.setImage(sequenzaSu[contatoreSu++ % 2]);
		        case "s" -> personaggio1.setImage(sequenzaGiu[contatoreGiu++ % 2]);
		        case "a" -> personaggio1.setImage(sequenzaSinistra[contatoreSinistra++ % 2]);
		        case "d" -> personaggio1.setImage(sequenzaDestra[contatoreDestra++ % 2]);
		    }
		} 	
	}

	// metodo che fa muovore il personaggio quando si preme un determinato tasto
	public void tastoPremuto(KeyEvent e) {
		// servono per reimpostara la posizione iniziale in caso di collisone
		double nuovaX = posizioneXPersonaggio;
		double nuovaY = posizioneYPersonaggio;

		// Ottieni il tasto premuto e imposta il movimento attuale
		String tasto = e.getText().toLowerCase();
		movimentoAttuale = tasto;

		switch (tasto) {
	    case "w" -> posizioneYPersonaggio -= movimento;
	    case "s" -> posizioneYPersonaggio += movimento;
	    case "a" -> posizioneXPersonaggio -= movimento;
	    case "d" -> posizioneXPersonaggio += movimento;
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
		int riga = (int)(posizioneYPersonaggio / 16)+1 ;

		// assegno ad una variabile il valore presenta in quella detereminata posizione della bitmap
		char cella = mappaSelezionata.getMappa()[riga][colonna];
		return cella == '1'; // ritorno true se cella è uguale a 1
	}

	public void cambioMappa () {

		Bounds boundPersonaggio = personaggio1.getBoundsInParent();
		Bounds boundMappaUnoToDue = hitBoxMappaUnoToDue.getBoundsInParent();
		Bounds boundMappaDueToUno = hitBoxMappaDueToUno.getBoundsInParent();
		Bounds boundMappaDueToTre = hitBoxMappaDueToTre.getBoundsInParent();
		Bounds boundMappaTreToDue = hitBoxMappaTreToDue.getBoundsInParent();
		Bounds boundMappaTreToQuattro = hitBoxMappaTreToQuattro.getBoundsInParent();
		Bounds boundMappaTreToCinque = hitBoxMappaTreToCinque.getBoundsInParent();
		Bounds boundMappaQuattroToTre = hitBoxMappaQuattroToTre.getBoundsInParent();
		Bounds boundMappaCinqueToTre = hitBoxMappaCinqueToTre.getBoundsInParent();	
		Bounds boundMonetaUno = moneta1.getBoundsInParent();
		Bounds boundMonetaDue = moneta2.getBoundsInParent();
		Bounds boundMonetaTre = moneta3.getBoundsInParent();
		Bounds boundMonetaQuattro = moneta4.getBoundsInParent();
		Bounds boundMonetaCinque = moneta5.getBoundsInParent();
		
		// Da mappaUno a mappaDue
		if (boundPersonaggio.intersects(boundMappaUnoToDue)) {
			areaDiGioco.getChildren().clear();
			areaDiGioco.getChildren().addAll(q2, personaggio1, hitBoxMappaDueToUno, hitBoxMappaDueToTre, eMoneteRaccolte);

			// Posizione hitbox per il ritorno a mappaUno
			hitBoxMappaDueToUno.setX(POSIZIONE_HITBOX_DUE_TO_UNO_X);
			hitBoxMappaDueToUno.setY(POSIZIONE_HITBOX_DUE_TO_UNO_Y);
			hitBoxMappaDueToUno.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappaTre
			hitBoxMappaDueToTre.setX(POSIZIONE_HITBOX_DUE_TO_TRE_X);
			hitBoxMappaDueToTre.setY(POSIZIONE_HITBOX_DUE_TO_TRE_Y);
			hitBoxMappaDueToTre.setFill(Color.TRANSPARENT);
			
			// Posizione moneta2
			if(monetaPresente[1] == true) {
				areaDiGioco.getChildren().add(moneta2);
				moneta2.setX(POSIZIONE_MONETA_2_X);
				moneta2.setY(POSIZIONE_MONETA_2_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

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
			areaDiGioco.getChildren().addAll(q1, personaggio1, hitBoxMappaUnoToDue, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaUnoToDue.setX(POSIZIONE_HITBOX_UNO_TO_DUE_X);
			hitBoxMappaUnoToDue.setY(POSIZIONE_HITBOX_UNO_TO_DUE_Y);
			hitBoxMappaUnoToDue.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[0]) {
				areaDiGioco.getChildren().add(moneta1);
				moneta1.setX(POSIZIONE_MONETA_1_X);
				moneta1.setY(POSIZIONE_MONETA_1_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);
			
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
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(POSIZIONE_HITBOX_TRE_TO_DUE_X);
			hitBoxMappaTreToDue.setY(POSIZIONE_HITBOX_TRE_TO_DUE_Y);
			hitBoxMappaTreToDue.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(POSIZIONE_HITBOX_TRE_TO_QUATTRO_X);
			hitBoxMappaTreToQuattro.setY(POSIZIONE_HITBOX_TRE_TO_QUATTRO_Y);
			hitBoxMappaTreToQuattro.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(POSIZIONE_HITBOX_TRE_TO_CINQUE_X);
			hitBoxMappaTreToCinque.setY(POSIZIONE_HITBOX_TRE_TO_CINQUE_Y);
			hitBoxMappaTreToCinque.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[2]) {
				areaDiGioco.getChildren().add(moneta3);
				moneta3.setX(POSIZIONE_MONETA_3_X);
				moneta3.setY(POSIZIONE_MONETA_3_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

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
			areaDiGioco.getChildren().addAll(q2, personaggio1, hitBoxMappaDueToUno, 
					hitBoxMappaDueToTre, eMoneteRaccolte);

			// Posizione hitbox per il ritorno a mappaUno
			hitBoxMappaDueToUno.setX(POSIZIONE_HITBOX_DUE_TO_UNO_X);
			hitBoxMappaDueToUno.setY(POSIZIONE_HITBOX_DUE_TO_UNO_Y);
			hitBoxMappaDueToUno.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappaTre
			hitBoxMappaDueToTre.setX(POSIZIONE_HITBOX_DUE_TO_TRE_X);
			hitBoxMappaDueToTre.setY(POSIZIONE_HITBOX_DUE_TO_TRE_Y);
			hitBoxMappaDueToTre.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[1] == true) {
				areaDiGioco.getChildren().add(moneta2);
				moneta2.setX(POSIZIONE_MONETA_2_X);
				moneta2.setY(POSIZIONE_MONETA_2_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

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
			areaDiGioco.getChildren().addAll(q4, personaggio1, hitBoxMappaQuattroToTre, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaQuattroToTre.setX(POSIZIONE_HITBOX_QUATTRO_TO_TRE_X);
			hitBoxMappaQuattroToTre.setY(POSIZIONE_HITBOX_QUATTRO_TO_TRE_Y);
			hitBoxMappaQuattroToTre.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[3]) {
				areaDiGioco.getChildren().add(moneta4);
				moneta4.setX(POSIZIONE_MONETA_4_X);
				moneta4.setY(POSIZIONE_MONETA_4_Y);
			}

			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);
						
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
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(POSIZIONE_HITBOX_TRE_TO_DUE_X);
			hitBoxMappaTreToDue.setY(POSIZIONE_HITBOX_TRE_TO_DUE_Y);
			hitBoxMappaTreToDue.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(POSIZIONE_HITBOX_TRE_TO_QUATTRO_X);
			hitBoxMappaTreToQuattro.setY(POSIZIONE_HITBOX_TRE_TO_QUATTRO_Y);
			hitBoxMappaTreToQuattro.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(POSIZIONE_HITBOX_TRE_TO_CINQUE_X);
			hitBoxMappaTreToCinque.setY(POSIZIONE_HITBOX_TRE_TO_CINQUE_Y);
			hitBoxMappaTreToCinque.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[2]) {
				areaDiGioco.getChildren().add(moneta3);
				moneta3.setX(POSIZIONE_MONETA_3_X);
				moneta3.setY(POSIZIONE_MONETA_3_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

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
			areaDiGioco.getChildren().addAll(q5, personaggio1, hitBoxMappaCinqueToTre, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaTre
			hitBoxMappaCinqueToTre.setX(POSIZIONE_HITBOX_CINQUE_TO_TRE_X);
			hitBoxMappaCinqueToTre.setY(POSIZIONE_HITBOX_CINQUE_TO_TRE_Y);
			hitBoxMappaCinqueToTre.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[4] == true) {
				areaDiGioco.getChildren().add(moneta5);
				moneta5.setX(POSIZIONE_MONETA_5_X);
				moneta5.setY(POSIZIONE_MONETA_5_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

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
					hitBoxMappaTreToQuattro, hitBoxMappaTreToCinque, eMoneteRaccolte);

			// Posizione hitbox per il passaggio a mappaDue
			hitBoxMappaTreToDue.setX(POSIZIONE_HITBOX_TRE_TO_DUE_X);
			hitBoxMappaTreToDue.setY(POSIZIONE_HITBOX_TRE_TO_DUE_Y);
			hitBoxMappaTreToDue.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappaQuattro
			hitBoxMappaTreToQuattro.setX(POSIZIONE_HITBOX_TRE_TO_QUATTRO_X);
			hitBoxMappaTreToQuattro.setY(POSIZIONE_HITBOX_TRE_TO_QUATTRO_Y);
			hitBoxMappaTreToQuattro.setFill(Color.TRANSPARENT);

			// Posizione hitbox per il passaggio a mappCinque
			hitBoxMappaTreToCinque.setX(POSIZIONE_HITBOX_TRE_TO_CINQUE_X);
			hitBoxMappaTreToCinque.setY(POSIZIONE_HITBOX_TRE_TO_CINQUE_Y);
			hitBoxMappaTreToCinque.setFill(Color.TRANSPARENT);
			
			if(monetaPresente[2]) {
				areaDiGioco.getChildren().add(moneta3);
				moneta3.setX(POSIZIONE_MONETA_3_X);
				moneta3.setY(POSIZIONE_MONETA_3_Y);
			}
			
			// Label che conta monete raccolte
			eMoneteRaccolte.setPrefSize(320, 16);
			eMoneteRaccolte.setTranslateY(320);

			// Cambio la bitmap
			mappaSelezionata = mappaTre;

			// Reimposto la posizione del personaggio nella nuova mappa
			posizioneXPersonaggio = 112;
			posizioneYPersonaggio = 272;
			personaggio1.setX(posizioneXPersonaggio);
			personaggio1.setY(posizioneYPersonaggio);
		}
		
		// metodi che controllano quando il personaggio raccoglie una moneta 
		// controlla anche se il gioco e finito chiamando il metodo vittoria
		if(monetaPresente[0] && boundPersonaggio.intersects(boundMonetaUno)) {
			monetaPresente[0] = false;
			areaDiGioco.getChildren().remove(moneta1);
			moneteRaccolte++;
			vittoria();
		}
		if(monetaPresente[1] && boundPersonaggio.intersects(boundMonetaDue)) {
			monetaPresente[1] = false;
			areaDiGioco.getChildren().remove(moneta2);		
			moneteRaccolte++;
			vittoria();
		}
		if(monetaPresente[2] && boundPersonaggio.intersects(boundMonetaTre)) {
			monetaPresente[2] = false;
			areaDiGioco.getChildren().remove(moneta3);
			moneteRaccolte++;
			vittoria();
		}
		if(monetaPresente[3] && boundPersonaggio.intersects(boundMonetaQuattro)) {
			monetaPresente[3] = false;
			areaDiGioco.getChildren().remove(moneta4); 
			moneteRaccolte++;
			vittoria();
		}
		if(monetaPresente[4] && boundPersonaggio.intersects(boundMonetaCinque)) {
			monetaPresente[4] = false;
			areaDiGioco.getChildren().remove(moneta5);
			moneteRaccolte++;
			vittoria();
		}
		
		// aggiorno le monete raccolte
		eMoneteRaccolte.setText("monete raccolte: " + moneteRaccolte);
	}
}
