package tron.modele;

/**
 * La classe Player représente un joueur dans le plateau de jeu
 * @author Antoine
 *	@see GameObject
 */
public class Player extends GameObject {
	private int id;
	private boolean firstPlayer;
	
	/**
	 * Permet de dupliquer un joueur.
	 * @param id identifiant unique du joueur = 100*ligne + colonne de départs
	 * @param row ligne ou se trouve le joueur actuellement
	 * @param col colonne ou se trouve le joueur actuellement
	 * @param firstPlayer booleen qui permet de savoir si il s'agit du premier joueur du jeu (utile dans le cas de Paranoid)
	 */
	private Player(int id, int row, int col, boolean firstPlayer){
		super(row, col);
		this.id = id;
		this.firstPlayer = firstPlayer;
	}
	
	/**
	 * Constructeur principale de la classe Player
	 * @param row 
	 * @param col
	 * @see GameObject#GameObject(int, int)
	 */
	public Player(int row, int col) {
		super(row, col);
		this.id = row*100 + col;
		this.firstPlayer = false;
	}
	
	/**
	 * Récupère l'identifiant unique de ce joueur
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Définis le joueur comme étant premier joueur du jeu
	 */
	public void setFirst(){
		this.firstPlayer = true;
	}
	
	/**
	 * Vérifie si le joueur est premier joueur du jeu
	 * @return
	 */
	public boolean isFirst(){
		return this.firstPlayer;
	}
	
	/**
	 * Met à jour les références de la position du joueur sur la grille de jeu
	 * @param direction le vecteur Direction qui représente le déplacement du joueur
	 */
	public void updatePosition(Direction direction) {
		this.row += direction.getRow();
		this.col += direction.getCol();
	}
	
	@Override
	public Player clone(){
		return new Player(this.id, this.row, this.col, this.firstPlayer);
	}
	
	@Override
	public String toString(){
		return "+";
	}
}