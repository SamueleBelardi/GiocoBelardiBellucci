package gioco;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Fine extends StackPane {
	
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
        	
        	
        });
        
    	this.getChildren().add(fine);
    	this.getChildren().add(rigioca);
    }
}
