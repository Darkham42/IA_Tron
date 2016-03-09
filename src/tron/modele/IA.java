package tron.modele;

/**
 * Enumérations de tous les algorithmes d'intelligence artificielle implémenté
 * @author Antoine
 *
 */
public enum IA {
	minimax{
		public Algorithm getAlgorithm(){
			return new Minimax();
		}
	},
	alphabeta{
		public Algorithm getAlgorithm(){
			return new AlphaBeta();
		}
	},
	paranoid{
		public Algorithm getAlgorithm(){
			return new Paranoid();
		}
		
		public int getMaximumPlayers(){
			return -1;
		}
		
		public boolean hasDifferentDepths(){
			return true;
		};
	};
	
	/**
	 * Récupère une instance de l'algorithme utilisé
	 * @return
	 */
	public abstract Algorithm getAlgorithm();
	
	/**
	 * 
	 * @return 2 ou -1 (-1 signifie un nombre de joueur supérieur ou égale à 3)
	 */
	public int getMaximumPlayers(){
		return 2;
	}
	
	/**
	 * Vérifie si l'intelligence artificielle utilisé 
	 * demande des profondeurs de recherches différentes selon les Joueurs
	 * @return
	 */
	public boolean hasDifferentDepths(){
		return false;
	};
}