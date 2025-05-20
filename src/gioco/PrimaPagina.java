package gioco;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrimaPagina extends Application {
	
	public void start(Stage primaryStage) throws Exception {

		Image copertina = new Image(getClass().getResourceAsStream("Copertina.jpeg"));
	    ImageView imageView = new ImageView (copertina);
	    
        // Crea il pulsante trasparente
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent;");
        button.setPrefSize(200, 100);

        // Azione al click del pulsante
        button.setOnAction(e -> {
        	 Gioco mappa = new Gioco();
        	 mappa.inizio();
             Scene giocoScene = new Scene(mappa, 320, 320); 
             
             giocoScene.setOnKeyPressed(event -> {
                 mappa.tastoPremuto(event);
             });
             
             giocoScene.setOnKeyReleased(event -> {
                 mappa.tastoRilasciato(event);
             });
             
             primaryStage.setScene(giocoScene);
             giocoScene.getRoot().requestFocus(); // necessario per ricevere gli eventi da tastiera
        });  
       

        // Sovrapposizione immagine e pulsante
        StackPane sfondo = new StackPane(imageView, button);
        button.setTranslateX(350);
        button.setTranslateY(330);
        Scene scene = new Scene(sfondo);
		
		primaryStage.setTitle("PrimaPagina");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
