package tron;

import java.util.regex.*;
import java.util.Scanner;

import tron.modele.*;
import tron.vue.*;

/**
 * Client de l'application, il interagit avec l'utilisateur
 * pour genere la taille de la grille de jeu ainsi que la position des joueurs
 * enfin il lance la partie avec l'algorithme (AlphaBeta, Minimax, Paranoid) choisit
 *	
 */
public class Tron {
	static final Scanner scan = new Scanner(System.in);
	
	/**
	 * Fonction d'entree de l'application
	 * @param args
	 */
	public static void main(String[] args) {
		IA ia = null;
		Game game = null;
		
		System.out.println("Do you want to save the game ? (y/n)");
		Write.getInstance().saveGame(scan.nextLine().equals("y"));
		
		while((ia = initializeIA()) == null);
		game = initializeGame(ia);
		
		scan.close();
		

		Write.getInstance().write("Algorithm: " + ia.name());
		startGame(game, ia.getAlgorithm());
	}
	
	/**
	 * Demarre le jeu et se termine quand un vainqueur est trouve
	 * @param gameinstance du jeu initialise
	 * @param algorithm	algorithme que les robots vont utilise
	 */
	private static void startGame(Game game, Algorithm algorithm) {
		
		int iteration = 0;
		Grid grid = new Grid();
		grid.drawGrid(game.getGameBoard());
		
		game.nextPlayer();
		game.currentPlayer.setFirst();
		
		Write.getInstance().write("First player: " + game.currentPlayer.getId());
		Write.getInstance().write("Depth: " + game.getProfondeur());
		Write.getInstance().write("Depth Coalitions: " + game.getProfondeurCoalition());
		do{
			game.isDead();
			game.play(algorithm.operation(game));
			if((++iteration % game.getPlayersNumber()) == 0)
				grid.drawGrid(game.getGameBoard());
		}while(!game.terminal());
		
	}
	
	/**
	 * Initialise les differents parametres necessaires au jeu
	 * @param ia intelligence artificielle choisit par l'utilisateur
	 * @return l'instance du jeu apres l'initialisation
	 * @see #initializeGridSize
	 * @see #initializeIADepths
	 * @see #initializeNumberPlayers
	 * @see #initializePlayersPosition
	 */
	private static Game initializeGame(IA ia){
		Game game = new Game();
		
		Write.getInstance().write("Welcome on the GRID !!!");
		initializeIADepths(game, ia);
		initializeGridSize(game);
		
		if(ia.getMaximumPlayers() != -1)
			game.setMaximumPlayers(ia.getMaximumPlayers());
		else
			game.setMaximumPlayers(Math.max(game.getRow(), game.getCol()));
		
		int nbPlayers = initializeNumberPlayers(game);
		for (int i = 1; i <= nbPlayers; i++) {
			int[] pos = null;
			try{
				pos = initializePlayersPosition(i, game.getRow(), game.getCol());
				if(game.getGameBoard()[pos[0] - 1][pos[1] - 1] != null){
					throw new NumberFormatException("This position is already in use by another Player");
				}
			}
			catch(NumberFormatException e){
				System.err.println("Error: " + e.getMessage() + '\n' + "Please Retry:");
				--i;
				continue;
			}
			Player player = new Player(pos[0] - 1 , pos[1] - 1);
			game.addPlayer(player);
		}
		
		return game;
	}
	
	/**
	 * Demande a l'utilisateur de choisir l'intelligence artificielle de cette partie
	 * @return l'intelligence artificielle choisit par l'utilisateur
	 */
	private static IA initializeIA(){
		String line = null;
		
		System.out.println("Choose the IAs of players:");
		for (IA ia : IA.values()) {
			System.out.println("\t-" + ia.name());
		}
		
		line = scan.nextLine();
		for (IA ia : IA.values()) {
			if (ia.name().equals(line.toLowerCase()))
				return ia;
		}
		
		return null;
	}
	
	/**
	 * Demande a l'utilisateur la profondeur utilisee par l'intelligence artificielle
	 * cette profondeur est superieure a 0 mais n'a pas de maximum.
	 * @param game l'instance du jeu sur laquelle travaille cette methode
	 * @param ia le type d'IA choisit
	 */
	private static void initializeIADepths(Game game, IA ia){
		String line = null;
		if(ia.hasDifferentDepths()){
			System.out.println("Depth of first player:");
			line = scan.nextLine();
			try{
				int depth = Integer.parseInt(line);
				if(depth <= 0)
					throw new NumberFormatException();
				else{
					game.setProfondeur(depth);
				}
			}
			catch(NumberFormatException e){
				System.out.println("It's not a valid number, depth of first player is set to " + game.getProfondeur());
			}
			
			System.out.println("Depth of Coalitions players:");
			line = scan.nextLine();
			try{
				int depth = Integer.parseInt(line);
				if(depth <= 0)
					throw new NumberFormatException();
				else{
					game.setProfondeurCoalition(depth);
				}
			}
			catch(NumberFormatException e){
				System.out.println("It's not a valid number, depth of coalitions players are set to " + game.getProfondeurCoalition());
			}
		}
		else{
			System.out.println("Depth of players:");
			line = scan.nextLine();
			try{
				int depth = Integer.parseInt(line);
				if(depth <= 0)
					throw new NumberFormatException();
				else{
					game.setProfondeur(depth);
				}
			}
			catch(NumberFormatException e){
				System.out.println("It's not a valid number, depth of players are set to " + game.getProfondeur());
			}
		}
	}
	
	/**
	 * Dans le cas d'une partie a plusieurs joueurs l'utilisateur doit choisir le nombre de joueurs
	 * @param game l'instance du jeu actuel
	 * @return le nombre de joueurs choisit par l'utilisateur
	 */
	private static int initializeNumberPlayers(Game game){
		if(game.getMaximumPlayers() == 2)
			return 2;
		
		boolean errorOccured = false;
		String line = null;
		int nbPlayers = 0;
		
		do{
			if(nbPlayers != 0 || errorOccured)
				System.out.println("Please Retry:");
			try {
				System.out.println("How many players ?");
				line = scan.nextLine();
				nbPlayers = Integer.parseInt(line);
				
				if(nbPlayers < 2 || nbPlayers > game.getMaximumPlayers())
					throw new NumberFormatException("This game allow 2 to " 
													+ game.getMaximumPlayers() 
													+ " players");
			}
			catch(NumberFormatException e){
				System.err.println("Error: " + e.getMessage());
				errorOccured = true;
				continue;
			}
			errorOccured = false;
		}while(nbPlayers < 2 || nbPlayers > Math.max(game.getRow(), game.getCol()));
		
		return nbPlayers;
	}
	
	/**
	 * Initialise la taille de la grille
	 * @param game l'instance du jeu sur laquelle travaille cette methode
	 * @see tron.modele.Game#getMaxRow()
	 * @see tron.modele.Game#getMaxCol()
	 * @see tron.modele.Game#getMinRow()
	 * @see tron.modele.Game#getMinCol()
	 * @see tron.modele.Game#setRow(int)
	 * @see tron.modele.Game#setCol(int)
	 * @see tron.modele.Game#generateBoard()
	 */
	private static void initializeGridSize(Game game){
		String line = null;
		int[] size = null; 
		do{
			if(size != null)
				System.out.println("Please Retry:");
			try{
				System.out.println("Size of the GRID <height>x<width>");
				line = scan.nextLine();
				size = getInts(line, "x");
				
				if(size[0] > game.getMaxRow() ||
				size[0] < game.getMinRow() ||
				size[1] > game.getMaxCol() ||
				size[1] < game.getMinCol())
					throw new NumberFormatException("GRID Size must be between " 
													+ game.getMinCol() 
													+ " and " 
													+ game.getMaxCol());
			}
			catch(NumberFormatException e){
				System.err.println("Error: " + e.getMessage());
				size = new int[2];
				size[0] = -1;
				size[1] = -1;
				
			}
		}while(
				size[0] > game.getMaxRow() ||
				size[0] < game.getMinRow() ||
				size[1] > game.getMaxCol() ||
				size[1] < game.getMinCol() 
		);
		
		game.setRow(size[0]);
		game.setCol(size[1]);
		game.generateBoard();
	}

	/**
	 * 
	 * @param i index du joueurs courant
	 * @param row nombre de lignes maximal du tableau
	 * @param col nombre de colonnes maximal du tableau
	 * @return un tableau de deux entiers representant la ligne et la colonne de la position pour le joueur i
	 * @throws NumberFormatException propage une erreur si les valeurs entree par l'utilisateurs sont incorrectes
	 */
	private static int[] initializePlayersPosition(int i, int row, int col) throws NumberFormatException{
		System.out.println("Initial position for player " + i + " ? <row>x<col>");
		
		String line = null;
		int[] ints = null;
		
		do{
			line = scan.nextLine();
			ints = getInts(line, "x");
		}while(ints[0] > row || ints[1] > col);
		
		return ints;
	}
	
	/**
	 * Recherche un motif particulier dans le parametre string deux valeurs entieres sont recherchees dans le motif delimite par le parametre delimiter
	 * @param string La chaine a verifier
	 * @param delimiter Le caractere qui delimite les deux valeurs dans la chaine
	 * @return Un tableau de deux entiers compris entre 1 et n
	 * @throws NumberFormatException Propage une erreur si les valeurs presentent dans la chaine de caracteres en parametre sont incorrectes
	 */
	private static int[] getInts(String string, String delimiter) throws NumberFormatException{
		int[] ints = new int[2];
		 Pattern pattern = Pattern.compile("[1-9][0-9]*" + delimiter + "[1-9][0-9]*");
		 Matcher matcher = pattern.matcher(string);
		 
		if(!matcher.find())
			 throw new NumberFormatException("cannot found <row>" + delimiter + "<col> in: " + string);
		
		String[] explode = string.split(delimiter);
		
		ints[0] = Integer.parseInt(explode[0]);
		ints[1] = Integer.parseInt(explode[1]);
		return ints;
	}
}