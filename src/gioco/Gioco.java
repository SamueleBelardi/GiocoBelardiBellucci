package gioco;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Gioco extends Application{
	
	/*
	 * Hai da fa ste cose stronzo (il più possibile) se non capisci qualcosa ho spiegato tutto
	 * TODO animazione movimento del personaggio
	 * TODO cambio scenario attraverso collisione in un determinato punto della mappa
	 * TODO mettere sulla mappa monete da raccogliere "obbiettivo del gioco" che quando ci passa sopra si levano dalla mappa
	 */

	// bitmap, servono per vedere se il personaggio puo camminare o no su un determinato riquadro
	Mappa mappaUno = new Mappa("ScenarioUno.txt");
	Mappa mappaDue = new Mappa("ScenarioDue.txt");
	Mappa mappaTre = new Mappa("ScenarioTre.txt");
	Mappa mappaQuattro = new Mappa("ScenarioQuattro.txt");
	Mappa mappaCinque = new Mappa("ScenarioCinque.txt");
	Mappa mappaSelezionata = mappaUno;
	
	double movimento = 2; 
	double posizioneXPersonaggio = 180; // posizione X personaggio nella mappa
	double posizioneYPersonaggio = 180; // posizione Y personaggio nella mappa
	static final double DIMENSIONE_X = 320; // dimensione X della mappa
	static final double DIMENSIONE_Y = 320; // dimensione Y della mappa
	static final double DIMENSIONE_X_PERSONAGGIO = 16; // numero di pixel X (grandezza) del personaggio
	static final double DIMENSIONE_Y_PERSONAGGIO = 32; // numero di pixel Y (grandezza) del personaggio
	
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
	
	ImageView q1 = new ImageView(scenarioUno);
	ImageView q2 = new ImageView(scenarioDue);
	ImageView q3 = new ImageView(scenarioTre);
	ImageView q4 = new ImageView(scenarioQuattro);
	ImageView q5 = new ImageView(scenarioCinque);
	ImageView personaggio1 = new ImageView(fermoGiu);
	ImageView obbiettivo = new ImageView(moneta);
	
	// pane in cui si aggiungono gli scenari, personaggio, ecc
	Pane areaDiGioco = new Pane();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// aggiunta degli elementi al pane
		areaDiGioco.getChildren().add(q1);
		areaDiGioco.getChildren().add(personaggio1);
		personaggio1.setX(posizioneXPersonaggio);
		personaggio1.setY(posizioneYPersonaggio);
		
		Scene scena = new Scene(areaDiGioco);
		primaryStage.setTitle("Gioco");
		primaryStage.setScene(scena);
		primaryStage.show();
		scena.setOnKeyPressed( e -> tastoPremuto(e));
	}
	
	// metodo che fa muovore il personaggio quando si preme un determinato tasto
	private void tastoPremuto(KeyEvent e) {
		// servono per reimpostara la posizione iniziale in caso di collisone
		double nuovaX = posizioneXPersonaggio;
		double nuovaY = posizioneYPersonaggio;
		
		// movimento personaggio
		if(e.getText().toLowerCase().equals("w")) {
			posizioneYPersonaggio -= movimento;
			personaggio1.setImage(fermoSu);
		}
		if(e.getText().toLowerCase().equals("s")) {
			posizioneYPersonaggio += movimento;
			personaggio1.setImage(fermoGiu);
		}
		if(e.getText().toLowerCase().equals("a")) {
			posizioneXPersonaggio -= movimento;
			personaggio1.setImage(fermoSinistra);
		}
		if(e.getText().toLowerCase().equals("d")) {
			posizioneXPersonaggio += movimento;
			personaggio1.setImage(fermoDestra);
		}
		
		// controllo collisioni
		if(puoMuoversi(nuovaX, nuovaY)) {
			posizioneXPersonaggio = nuovaX;
			posizioneYPersonaggio = nuovaY;
		}
		
		// controllo che il personaggio non esce dai bordi
		if(posizioneYPersonaggio>DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO) posizioneYPersonaggio = DIMENSIONE_Y-DIMENSIONE_Y_PERSONAGGIO;
		if(posizioneXPersonaggio>DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO) posizioneXPersonaggio = DIMENSIONE_X-DIMENSIONE_X_PERSONAGGIO;
		if(posizioneYPersonaggio<0) posizioneYPersonaggio = 0;
		if(posizioneXPersonaggio<0) posizioneXPersonaggio = 0;
			
		// reimposto la posizione del personaggio dopo il movimento
		personaggio1.setX(posizioneXPersonaggio);
		personaggio1.setY(posizioneYPersonaggio);
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
		
		public void cambioMappa (double dimensioneX, double dimensioneY) {
			int colonna = (int)(posizioneXPersonaggio / 16);
		    int riga = (int)(posizioneYPersonaggio / 16)+1 ;
		    
		    char cella = mappaSelezionata.getMappa()[riga][colonna];
		    switch (cella) {
		    case '2': // spostamento allo ScenarioUno
		    	areaDiGioco.getChildren().add(q1);
		    	personaggio1.setX(0);
		    	personaggio1.setY(0);
		    	break;
		    case '3': // spostamento allo scenarioDue
		    	areaDiGioco.getChildren().add(q2);
		    	personaggio1.setX(0);
		    	personaggio1.setY(0);
		    	break;
		    }
		}

	public static void main(String[] args) {
		launch(args);
	}
}
