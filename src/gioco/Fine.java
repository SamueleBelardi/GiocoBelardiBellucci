package gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Fine extends BorderPane {
	// Immagine fine gioco
    Image f = new Image(getClass().getResourceAsStream("Fine.png"));
    ImageView fine = new ImageView(f);
    
    public void sfondo() {
    	this.setCenter(fine);
    }
}
