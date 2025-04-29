package gioco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Mappa {

	private String percorso;
	
	public Mappa (String percorso) {
		super();
		this.percorso = percorso;
		try (
				InputStream is = getClass().getResourceAsStream(percorso);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader lettore = new BufferedReader(isr);
		){
			int riga = 1;
			String [][] rigaLetta;
			while( (rigaLetta = lettore.readLine())!=null ) {
				int posizione = 1;
				for(int)
				
				riga++;
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public String getPercorso() {
		return percorso;
	}
	
	
}
