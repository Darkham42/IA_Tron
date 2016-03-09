package tron.modele;

/**
 * Interface representant l'algorithme utilise par les robots du jeu
 *
 */
public interface Algorithm {
	/**
	 * methode qui retourne la meilleure direction choisit pour le joueur actuel
	 * @param game l'instance du jeu actuel
	 * @return une Direction
	 * @see Direction
	 */
	public Direction operation(Game game);
}
