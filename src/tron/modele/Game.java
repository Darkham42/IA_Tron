package tron.modele;
import java.util.ArrayList;

import tron.Write;

/**
 * La classe principale de l'application
 * Une instance de Game represente l'etat actuel du Jeu, avec le joueur qui doit jouer, le plateau du jeu et toutes les methodes necssaires aux IAs
 *
 */
public class Game {
	private int profondeurCoalition;
	private int profondeur;
	
	//joueur actuel dans cet etat du jeu.
	public Player currentPlayer;
	//Declaration du nombre de lignes et de colonnes du plateau.
	private int col, row;
	//Declaration du plateau
	private GameObject[][] gameBoard;
	//Declaration des joueurs
	private ArrayList<Player> players;
	private int maximumPlayers;
	private int nextPlayerIdx;
	
	/**
	 * Constructeur prive de Game qui reproduit l'instance passee en parametre
	 * @param game L'instance de Game a reproduire
	 */
	private Game(Game game){
		this.profondeur = 3;
		this.profondeurCoalition = 3;
		
		this.row = game.row;
		this.col = game.col;
		this.currentPlayer = game.currentPlayer.clone();
		this.players = new ArrayList<Player>();
		
		for (Player player : game.players) {
			this.players.add(player.clone());
		}
		this.nextPlayerIdx = game.nextPlayerIdx;
		
		this.gameBoard = new GameObject[this.row][this.col];
		
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				if( !(game.gameBoard[i][j] instanceof Player) )
					this.gameBoard[i][j] = game.gameBoard[i][j];
			}
		}
		
		for (Player player : game.players) {
			this.setPosition(player);
		}
	}
	
	/**
	 * Constructeur principal de la classe Game
	 */
	public Game() {
		this.row = 10;
		this.col = 10;
		this.gameBoard= null;
		this.players = new ArrayList<Player>();
		this.nextPlayerIdx = 0;
		
	}
	
	/**
	 * Genere le tableau de jeu
	 * si le tableau de jeu est deja instancie, ne le regenere pas.
	 */
	public void generateBoard(){
		if(this.gameBoard != null)
			return;
		this.gameBoard = new GameObject[row][col];
	}
	
	/**
	 * Met a jour currentPlayer, fonctionne comme un iterateur
	 */
	public void nextPlayer(){
		if(this.nextPlayerIdx >= this.players.size()){
			this.nextPlayerIdx = 0;
		}
		this.currentPlayer = this.players.get(this.nextPlayerIdx++);
	}
	
	/**
	 * Getter de la profondeur utilisee par les IAs de la coalition
	 * @return la profondeur utilisee par les IAs de la coalition
	 */
	public int getProfondeurCoalition() {
		return profondeurCoalition;
	}

	/**
	 * Setter de la profondeur utilisee par les IAs de la coalition
	 * @param profondeurCoalition la profondeur utilisee par les IAs de la coalition
	 */
	public void setProfondeurCoalition(int profondeurCoalition) {
		this.profondeurCoalition = profondeurCoalition;
	}

	/**
	 * Getter de la profondeur utilisee par les IAs
	 * @return la profondeur utilisee par les IAs
	 */
	public int getProfondeur() {
		return profondeur;
	}

	/**
	 * Setter de la profondeur utilisee par les IAs
	 * @param profondeur la profondeur utilisee par les IAs
	 */
	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

	public int getMinCol() {
		return 10;
	}
	
	public int getMaxCol() {
		return 30;
	}
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getMinRow() {
		return 10;
	}
	
	public int getMaxRow() {
		return 30;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Getter du nombre de joueurs
	 * @return le nombre de joueurs
	 */
	public int getPlayersNumber(){
		return this.players.size();
	}
	
	/**
	 * Met a jour le nombre de joueur maximal
	 * @param number le nombre de joueurs maximal
	 */
	public void setMaximumPlayers(int number){
		this.maximumPlayers = number;
	}
	
	public int getMaximumPlayers(){
		return this.maximumPlayers;
	}
	
	/**
	 * Ajout d'un joueur
	 * @param player le joueur a ajouter
	 */
	public void addPlayer(Player player) {
		players.add(player);
		this.setPosition(player);
	}
	
	/** 
	 * Suppression d'un joueur
	 * @param player le joueur a supprimer
	 */
	private void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * Met a jour la position initiale d'un GameObject
	 * @param gameObject l'objet a placer sur le plateau de jeu
	 */
	private void setPosition(GameObject gameObject) {
		this.gameBoard[gameObject.getRow()][gameObject.getCol()] = gameObject;
	}
	
	/**
	 * Methode principale de la classe Game, elle fait Jouer le joueur courant
	 * @param direction la direction jouee par le joueur courant
	 */
	public void play(Direction direction){
		if(direction == null){
			this.removePlayer(this.currentPlayer);
			this.nextPlayer();
			return;
		}

		if(gameBoard[this.currentPlayer.getRow() + direction.getRow()][this.currentPlayer.getCol() + direction.getCol()] != null){
			System.err.println("\nImpossible to go on an occupied position");
			return;
		}
		gameBoard[this.currentPlayer.getRow()][this.currentPlayer.getCol()] = new Wall(this.currentPlayer.getRow(), this.currentPlayer.getCol());
		
		this.currentPlayer.updatePosition(direction);
		this.updatePlayerList();
		
		gameBoard[this.currentPlayer.getRow()][this.currentPlayer.getCol()] = this.currentPlayer;
		
		this.nextPlayer();
		return;
	}
	
	/**
	 * met a jour la liste des joueurs (une arrayList) en fonction de l'identifiant unique de chaque Joueurs
	 */
	private void updatePlayerList(){
		for (int i = 0; i < this.players.size(); i++) {
			if(this.players.get(i).getId() == this.currentPlayer.getId()){
				this.players.set(i, this.currentPlayer);
				return;
			}
		}
	}
	
	/**
	 * Retourne les Etats successeurs de la parties qui peuvent etre compris entre 0 et 3
	 * @return Les etats successeurs de la parties
	 */
	public Game[] nextMove(){
		
		ArrayList<Game> coupSuivant;
		coupSuivant = new ArrayList<Game>();
		
		for(int i = 0 ; i < 4 ; i++){
			Direction direction = Direction.values()[Direction.TOP.ordinal() + i];
			if(direction.estPossible(this, this.currentPlayer)){
				Game clone = this.clone();
				clone.play(direction);
				coupSuivant.add(clone);	
			}
		}
			
		Game[] games = new Game[coupSuivant.size()];
		return coupSuivant.toArray(games);
	}
	
	/**
	 * Getter du plateau de jeu
	 * @return
	 */
	public GameObject[][] getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * Heuristique utilisee par les IAs
	 * @param player le joueurs qui doit etre evalues
	 * @return une valeur entiere representant l'avantage du joueur
	 */
	int value(Player player){
		int value = 0;
		
		Player[] listP = new Player[this.players.size()];
		this.players.toArray(listP);
		
		for (Player p : listP) {
			if(p.getId() == player.getId())
				value = this.domainCount(p);
		}
		return value;
	}
	
	/**
	 * Retourne le nombre de cases atteintes par le joueur passe en parametre
	 * @param player le joueur a evaluer
	 * @return le nombre de case atteintes par le joueur passe en parametre
	 */
	private int domainCount(Player player){
		int positionCount = 0;
		int[][] position = new int[this.row][this.col];
		int[][] positionToMatch = new int[this.row][this.col];
		
		for (int row = 0; row < position.length; row++) {
			for (int col = 0; col < position[0].length; col++) {
				position[row][col] = -1;
				positionToMatch[row][col] = -1;
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			position = dijkstra(position, 0, players.get(i).getRow(), players.get(i).getCol());
		}
		
		positionToMatch = dijkstra(positionToMatch, 0, player.getRow(), player.getCol());
		
		for (int row = 0; row < position.length; row++) {
			for (int col = 0; col < position[0].length; col++) {
				if(position[row][col] != -1 && positionToMatch[row][col] != 0){
					if(position[row][col] == positionToMatch[row][col]){
						positionCount++;
					}
				}
			}
		}
		return positionCount;
	}
	
	/**
	 * Verifie si les valeurs x et y entres en parametres representent une valeur correcte
	 * @param row la ligne 
	 * @param col la colonne
	 * @return vrai si [row][col] appartient au tableau de jeu sinon faux
	 */
	private boolean isInBoard(int row, int col){
		return row >= 0 && row < this.getRow() && col >= 0 && col < this.getCol();
	}
	
	/**
	 * Effectue un calcul du plus court chemin entre chaque joueur et chaque case
	 * @param positions Un tableau d'entier de la meme taille que le plateau de jeu
	 * @param iteration le nombre d'iterations de l'algos
	 * @param row la ligne representant une des deux valeurs du curseur
	 * @param col la colonne representant une des deux valeurs du curseur
	 * @return un tableau d'entier representant les cases atteintes pour chaques joueurs 
	 */
	private int[][] dijkstra(int[][] positions, int iteration, int row, int col){
		if(isInBoard(row, col)){
			if(this.gameBoard[row][col] instanceof Wall){
				return positions;
			}
			else{
				if(positions[row][col] > iteration || positions[row][col] == -1){
					positions[row][col] = iteration;
					
				}
			}
		}
		for(int i = 0 ; i < 4 ; i++){
			Direction direction = Direction.values()[Direction.TOP.ordinal() + i];
			if(isInBoard(row + direction.getRow(), col + direction.getCol())){
				if(positions[row + direction.getRow()][col + direction.getCol()] > iteration + 1 || positions[row + direction.getRow()][col + direction.getCol()] == -1)
					positions = dijkstra(positions, iteration + 1, row + direction.getRow(), col + direction.getCol());
			}
		}
		return positions;
	}
	
	/**
	 * Clone l'instance actuelle du jeu
	 * @see #Game(Game)
	 */
	public Game clone(){
		return new Game(this);
	}
	
	/**
	 * Verifie si un joueur est mort et le supprime
	 * @return true si le joueur actuelle ne peut plus bouger
	 */
	public boolean isDead() {
		Direction direction = null;
		boolean zeroDirection = true;
		for (int i = 0; i < 4; i++) {
			direction = Direction.values()[Direction.TOP.ordinal() + i];
			if(!direction.estPossible(this, this.currentPlayer))
				continue;
			zeroDirection = false;
		}
		
		if(zeroDirection){
			Write.getInstance().write("Player " + this.currentPlayer.getId() + " loose");
			this.removePlayer(this.currentPlayer);
		}
		
		return zeroDirection;
	}
	
	/**
	 * Verifie si la partie est terminee (si il ne reste qu'un joueur)
	 * @return true si la partie est terminee
	 */
	public boolean terminal() {
			if (players.size() == 1) {
				Write.getInstance().write("Player " + this.players.get(0).getId() + " win");
				return true;
			} else 
				return false;
	}
}