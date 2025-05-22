package gioco;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Fine extends StackPane {
	
	PrimaPagina p;
	Stage primaryStage;
	
	public Fine(PrimaPagina p, Stage primaryStage) {
	       this.p = p;
	       this.primaryStage = primaryStage;
	       sfondo(); // chiama il metodo direttamente nel costruttore
   }
	
    public void sfondo() {
    	
    	String path = getClass().getResource("sottofondo.mp3").toExternalForm();
		Media media = new Media(path);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
    	
    	// Immagine fine gioco
        Image f = new Image(getClass().getResourceAsStream("Fine.png"));
        ImageView fine = new ImageView(f);
        
        // bottone che permette di rigiocare da capo
        Button rigioca = new Button();
        rigioca.setStyle("-fx-background-color: transparent;");
        rigioca.setPrefSize(200, 100);
        rigioca.setTranslateX(-250);
        rigioca.setTranslateY(440);
        
        rigioca.setOnAction( e -> {
        	// ricrea un'istanza di gioco per rigioare nuovamente
        	Gioco mappa = new Gioco(p);
        	mappa.inizio();
        	Scene giocoScene = new Scene(mappa, 320, 336); 

        	// servono per far camminare il personaggio
        	giocoScene.setOnKeyPressed(event -> {
        		mappa.tastoPremuto(event);
        	});

        	giocoScene.setOnKeyReleased(event -> {
        		mappa.tastoRilasciato(event);
        	});

        	primaryStage.setScene(giocoScene);
        	giocoScene.getRoot().requestFocus(); // necessario per ricevere gli eventi da tastiera
        });
        
    	this.getChildren().add(fine);
    	this.getChildren().add(rigioca);
    }
}
