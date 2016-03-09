package tron.modele;

/**
 * Classe Abstraite représentant un élément du plateau de jeu
 * @author Antoine
 * @see Player
 * @see Wall
 *
 */
public abstract class GameObject {
	protected int row, col;
	
	/**
	 * Constructeur d'un gameObject il initialise le GameObject avec ca position initialie sur le plateau de jeu
	 * @param row numéro de ligne composante de la position du GameObject
	 * @param col numéro de colonne composante de la position du GameObject
	 */
	public GameObject(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Retourne le numéro de ligne ou se situe le robot
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Met à jour la position du robot
	 * @param row le numéro de ligne ou le robot se trouve désormais
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Retourne le numéro de colonne ou se situe le robot
	 * @return
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Met à jour la position du robot
	 * @param row le numéro de colonne ou le robot se trouve désormais
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	public abstract String toString();
}
