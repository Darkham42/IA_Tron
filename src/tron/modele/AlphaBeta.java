package tron.modele;

/**
 * Algorithme AlphaBeta
 * @see Algorithm
 */
public class AlphaBeta implements Algorithm {

	@Override
	public Direction operation(Game game) {
		Direction directionBestChoice = null;
		int bestChoice = -1001;
		Direction direction = null;
		
		for(int i=0 ; i < 4 ; i++){
			direction = Direction.values()[Direction.TOP.ordinal() + i];
			if(direction.estPossible(game, game.currentPlayer)){
				Game clone = game.clone();
				clone.play(direction);
				int alphabeta = alphabeta(clone, 13,28 , game.getProfondeur()-1, game.currentPlayer);
				if( alphabeta > bestChoice){
					bestChoice = alphabeta;
					directionBestChoice = direction;
				}
			}
		}
		return directionBestChoice;
	}
	
	/**
	 * AlphaBeta est un algorithme qui permet de trouver un coup optimale 
	 * pour un joueur donne, en fonction d'une profondeur de recherche donnee
	 * L'algorithme est le meme que Minimax mais avec un elagage 
	 * lors de la recherche du meilleurs coups possible, 
	 * ce qui rend cette algorithme bien plus rapide.
	 * 
	 * @see Minimax#minimax
	 * @param game une simulation du jeu
	 * @param alpha Valeur specifique a l'algorithme AlphaBeta
	 * @param beta Valeur specifique a l'algorithme AlphaBeta
	 * @param profondeur profondeur de recherche de l'algorithme
	 * @param player Le joueur qui doit jouer
	 * @return une valeur entiere representant la valeur de ce noeud
	 */
	private int alphabeta(Game game, int alpha, int beta,int profondeur, Player player){
		if(profondeur == 0){
			return game.value(player);
		}
		
		//si c'est un noeud joueur
		if(game.currentPlayer.getId() == player.getId()){
			for (Game gameNext : game.nextMove()) {
				alpha = Math.max(alpha, alphabeta(gameNext, alpha, beta, profondeur-1, player));
				if(alpha >= beta)
					return alpha;
			}
			return alpha;
		}
		else{//si c'est un noeud opposant
			for (Game gameNext : game.nextMove()) {
				beta = Math.min(beta, alphabeta(gameNext, alpha, beta, profondeur-1, player));
				if(alpha >= beta)
					return beta;
			}
			return beta;
		}
	}

}
