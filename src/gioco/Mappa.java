package gioco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Mappa {
	
	private String percorso; // nome del file in cui e presente la bitmap
	char [][] mappa = new char[20][20]; // matrice dove e presente la bitmap (dimensione fissa perché le mappe sono tutte uguali)
	
	// costruttore che costruisce la matrice
	public Mappa (String percorso) {
		super();
		this.percorso = percorso;
	
		//creazione matrice 
		try (
				// leggo le informazione dal file di testo
				// non fileReader perche questo è piu comodo visto non c'è bisogno di scrivere tutto il percorso ma legge direttamente dalla cartella
				InputStream is = getClass().getResourceAsStream(percorso); 
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader lettore = new BufferedReader(isr);
		){
			int riga = 0; // indice che indica la riga della matrice
			String rigaLetta; 
			while( (rigaLetta = lettore.readLine())!=null && riga < 20 ) {
				// vettore di caratteri dove è presente la riga presa dal file.txt
				char [] rigaDivisa = rigaLetta.toCharArray();
				System.out.println(">>"+rigaLetta+"<<");
				// aggiunge tutti i valori del vettore di caratteri alla matrice
				for(int i = 0; i < rigaDivisa.length; i++) { // indice delle colonne
					mappa [riga][i] = rigaDivisa [i]; 
				}
				riga++;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return restetuisce la matrice di caratteri
	 */
	public char[][] getMappa() {
		return mappa;
	}

	/**
	 * @return restituisce il percorso "nome del file"
	 */
	public String getPercorso() {
		return percorso;
	}
	
	
}
