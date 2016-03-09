package tron.modele;

/**
 * Enumeration des 4 directions possible sur le plateau de jeu
 *
 */
public enum Direction {
	
	TOP(-1,0),
	RIGHT(0,1),
	BOTTOM(1,0),
	LEFT(0,-1);
	
	private int row, col;
	
	/**
	 * Constructeur prive pour les enums, ils embarquent 2 valeurs entier pour former un vecteur de directions
	 * @param row Valeur de deplacements sur l'axe Y compris entre -1 et 1
	 * @param col Valeur de deplacements sur l'axe X compris entre -1 et 1
	 */
	private Direction(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * 
	 * @return la coordonnees Y du vecteur de direction
	 */
	public int getRow(){
		return this.row;
	}
	
	/**
	 * 
	 * @return la coordonnees X du vecteur de direction
	 */
	public int getCol(){
		return this.col;
	}
	
	/**
	 * Verifie si le joueur peut se deplacer dans cette direction
	 * @param game l'instance du jeu
	 * @param player Le joueur qui cherche a se deplacer
	 * @return true si la direction est possible sinon false
	 */
	public boolean estPossible(Game game, Player player){
		int nextRow = player.getRow() + this.getRow();
		int nextCol = player.getCol() + this.getCol();
		
		try{
			if(game.getGameBoard()[nextRow][nextCol] != null)
				throw new ArrayIndexOutOfBoundsException();
		}
		catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
		return true;
	}
}
