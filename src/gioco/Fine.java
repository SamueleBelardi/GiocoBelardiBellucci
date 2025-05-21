package gioco;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
    	
    	// Immagine fine gioco
        Image f = new Image(getClass().getResourceAsStream("Fine.png"));
        ImageView fine = new ImageView(f);
        
        Button rigioca = new Button();
        rigioca.setStyle("-fx-background-color: red;");
        rigioca.setPrefSize(200, 100);
        rigioca.setTranslateX(-250);
        rigioca.setTranslateY(440);
        
        rigioca.setOnAction( e -> {
        	Gioco mappa = new Gioco(p);
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
        
    	this.getChildren().add(fine);
    	this.getChildren().add(rigioca);
    }
}
