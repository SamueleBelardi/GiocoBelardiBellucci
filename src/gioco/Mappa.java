package gioco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Mappa {

	private String percorso;
	char [][] mappa = new char[20][20];
	
	public Mappa (String percorso) {
		super();
		this.percorso = percorso;
		System.out.println(percorso);
		//creazione matrice della mappa
		try (
				InputStream is = getClass().getResourceAsStream(percorso);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader lettore = new BufferedReader(isr);
		){
			int riga = 0;
			
			String rigaLetta;
			while( (rigaLetta = lettore.readLine())!=null && riga < 20 ) {
				char [] rigaDivisa = rigaLetta.toCharArray();
				System.out.println(">>"+rigaLetta+"<<");
				for(int i = 0; i < rigaDivisa.length; i++) {
					mappa [riga][i] = rigaDivisa [i]; 
				}
				riga++;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public char[][] getMappa() {
		return mappa;
	}

	public String getPercorso() {
		return percorso;
	}
	
	
}
