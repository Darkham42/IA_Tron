package tron.vue;

import tron.Write;
import tron.modele.GameObject;

/**
 * Classe permettant de dessiner la grille de jeu
 *
 */
public class Grid {

	/**
	 * Dessine la grille de jeu suivant les caracteristiques voulus par le joueur
	 * @param gameBoard
	 */
	public void drawGrid(GameObject[][] gameBoard) {
		String grid = "\n";
		Write.getInstance().write("");
		for (int row = 0; row<gameBoard.length ; row++) {
			grid += '\n';
			for (int col = 0; col<gameBoard[0].length ; col++) {
				if (gameBoard[row][col] == null) {
					grid += "[ ]";
				} else {
					grid += "[" + gameBoard[row][col] + "]";
				}
			}
		}
		Write.getInstance().write(grid);
	}
}
