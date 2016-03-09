package tron.modele;

/**
 * Algorithme Paranoid
 * @see Algorithm
 */
public class Paranoid implements Algorithm {
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
				int paranoid = paranoid(clone, game.currentPlayer.isFirst() ? game.getProfondeur()-1 : game.getProfondeurCoalition()-1, game.currentPlayer);
				if( paranoid > bestChoice){
					bestChoice = paranoid;
					directionBestChoice = direction;
				}
			}
		}
		return directionBestChoice;
	}
	
	/**
	 * Paranoid est un algorithme qui permet de representer un jeu a N joueurs
	 * comme un jeu a 2 joueurs, les N joueurs sont divisa en 2 groupes: 
	 * un joueur et une coalition des autres joueurs.
	 * de ce fait l'implementation de paranoid est relativement similaire a Minimax
	 * @param game une simulation du jeu
	 * @param profondeur profondeur de recherche de l'algorithme
	 * @param player Le joueur qui doit jouer
	 * @return une valeur entiere representant la valeur de ce noeud
	 */
	private int paranoid(Game game, int profondeur, Player player){
		int m = 0;
		if(profondeur == 0){
			return game.value(player);
		}
		
		//si c'est un noeud joueur
		if(game.currentPlayer.isFirst()){
			m = - 1000;
			for (Game gameNext : game.nextMove()) {
				int tmp = paranoid(gameNext, profondeur - 1, player);
				m = Math.max(m, tmp);
			}
		}
		else{//si c'est un noeud opposant
			m = 1000;
			for (Game gameNext : game.nextMove()) {
				int tmp = paranoid(gameNext, profondeur - 1, player);
				m = Math.min(m, tmp);
			}
		}
		return m;
	}
}
