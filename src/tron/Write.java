package tron;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Permet d'ecrire sur la sortie standard et dans un fichier en meme temps pour garder une trace ecrite d'une partie
 *
 */
public class Write {
	private boolean saveGame;
	private FileWriter file;
	
	private static Write write = new Write(false);
	
	/**
	 * Constructeur prive de la classe Write
	 * @param saveGame booleen qui permet de savoir si il faut garder une trace ecrite de la partie
	 */
	private Write(boolean saveGame) {
		this.saveGame = saveGame;
	}
	
	/**
	 * Methode statique qui retourne l'instance de Write qui permet d'ecrire dans un fichier 
	 * @return L'instance de Write pour ecrire dans un fichier
	 */
	public static Write getInstance(){
		return Write.write;
	}
	
	/**
	 * Met a jour la valeur du booleen saveGame
	 * @param saveGame booleen qui permet de savoir si il faut garder une trace ecrite de la partie
	 */
	public void saveGame(boolean saveGame){
		this.saveGame = saveGame;
		if(saveGame){
			try {
				this.file = new FileWriter("tron_trace.txt", true);
				this.file.write("===================\n");
				this.file.flush();
			} catch (IOException e) {
				System.out.println(e.getMessage() + "\nThis game will not be save");
				this.saveGame = false;
			}
		}
	}
	
	/**
	 * Ecris une chaine de caracteres sur la sortie standard et l'enregistre dans un fichier si necessaire
	 * @param string La chaine de caracteres a ecrire
	 */
	public void write(String string){
		System.out.println(string);
		if(this.saveGame){
			try {
				this.file.write(string + "\n");
				this.file.flush();
			} catch (IOException e) {
				System.out.println(e.getMessage() + "\nThis game will not be save");
				this.saveGame = false;
			}
		}
	}
}
