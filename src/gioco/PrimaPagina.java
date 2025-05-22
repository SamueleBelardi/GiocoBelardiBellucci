package gioco;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PrimaPagina extends Application {
	
	Gioco mappa;
	Stage primaryStage;
	
	public void start(Stage primaryStage) throws Exception {
		
		String path = getClass().getResource("sottofondo.mp3").toExternalForm();
		Media media = new Media(path);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		
		mappa = new Gioco(this);
		this.primaryStage = primaryStage;
		System.out.println(this.primaryStage);
		
		// Immagine copertina
		Image c = new Image(getClass().getResourceAsStream("Copertina.jpeg"));
	    ImageView copertina = new ImageView(c);
	   
        // Pulsante inizio Gioco
        Button inizio = new Button();
        inizio.setStyle("-fx-background-color: transparent;");
        inizio.setPrefSize(200, 100);

        // Azione al click del pulsante
        inizio.setOnAction(e -> {
        	
        	 mappa.inizio();
             Scene giocoScene = new Scene(mappa, 320, 336); 
             
             giocoScene.setOnKeyPressed(event -> {
                 mappa.tastoPremuto(event);
             });
             
             giocoScene.setOnKeyReleased(event -> {
                 mappa.tastoRilasciato(event);
             });
             
             primaryStage.setScene(giocoScene);
             giocoScene.getRoot().requestFocus(); // necessario per ricevere gli eventi da tastiera
        });  
        
        // StackPane iniziale
        StackPane sfondo = new StackPane(copertina, inizio);
        inizio.setTranslateX(350);
        inizio.setTranslateY(330);
        Scene scene = new Scene(sfondo);
		
		primaryStage.setTitle("PrimaPagina");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void fineGioco() {
		Fine fine = new Fine(this, primaryStage);
    	fine.sfondo();
    	Scene fineScene = new Scene(fine);
		primaryStage.setScene(fineScene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
