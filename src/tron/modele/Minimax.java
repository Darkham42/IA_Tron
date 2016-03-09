package tron.modele;

import tron.Write;

/**
 * Algorithme Minimax
 * @see Algorithm
 */
public class Minimax implements Algorithm {
	//public static int nodeCount;
	@Override
	public Direction operation(Game game) {
		//nodeCount = 0;
		Direction directionBestChoice = null;
		int bestChoice = -1001;
		Direction direction = null;
		
		for(int i=0 ; i < 4 ; i++){
			direction = Direction.values()[Direction.TOP.ordinal() + i];
			if(direction.estPossible(game, game.currentPlayer)){
				//nodeCount++;
				Game clone = game.clone();
				clone.play(direction);
				int minimax = minimax(clone, game.getProfondeur()-1, game.currentPlayer);
				if( minimax > bestChoice){
					bestChoice = minimax;
					directionBestChoice = direction;
				}
			}
		}
		Write.getInstance().write("NodeCount: " /*+ nodeCount*/);
		return directionBestChoice;
	}
	
	/**
	 * L'algorithme Minimax est un algorithme permettant de connaitre le coup optimal d'un joueur dans une situation a deux joueurs
	 * @param game une simulation du jeu
	 * @param profondeur profondeur de recherche de l'algorithme
	 * @param player Le joueur qui doit jouer
	 * @return une valeur entiere representant la valeur de ce noeud
	 */
	private int minimax(Game game, int profondeur, Player player){
		int m = 0;
		if(profondeur == 0){
			return game.value(player);
		}
		
		//si c'est un noeud joueur
		if(game.currentPlayer.getId() == player.getId()){
			m = - 1000;
			for (Game gameNext : game.nextMove()) {
				//nodeCount++;
				int tmp = minimax(gameNext, profondeur - 1, player);
				m = Math.max(m, tmp);
			}
		}
		else{//si c'est un noeud opposant
			m = 1000;
			for (Game gameNext : game.nextMove()) {
				//nodeCount++;
				int tmp = minimax(gameNext, profondeur - 1, player);
				m = Math.min(m, tmp);
			}
		}
		return m;
	}
	
}
