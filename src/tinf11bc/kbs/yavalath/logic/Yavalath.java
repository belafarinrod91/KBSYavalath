package tinf11bc.kbs.yavalath.logic;

import tinf11bc.kbs.yavalath.gui.MenueFactory;
import tinf11bc.kbs.yavalath.gui.PlayGround;
import tinf11bc.kbs.yavalath.gui.SettingsGui;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.logic.player.RandomAI;
import tinf11bc.kbs.yavalath.util.GameState;
import tinf11bc.kbs.yavalath.util.GameState.State;
import tinf11bc.kbs.yavalath.util.Settings;

/**
 * @author Chris, Stephan
 *
 */
public class Yavalath {
	
	private static boolean debug = false;
	private static PlayGround plgnd;
	private static GameState gameState;
	private static Settings mSettings;
	
	public static int numberOfSimulations = 50000;


	
	public static State newGame(int[] newPlayers) throws YavalathException {

//		Connector connector = new Connector();
		
		plgnd.setGameInformation("Hello, welcome to our Yavalath-Game !");
		
		gameState = new GameState(newPlayers,plgnd);
		
		
		gameState = playGame(gameState);
		
		if(debug){
			if(gameState.getState() == State.DRAW) {
				System.out.println("It's a draw!");
			}
			else {
				System.out.println("Player " + gameState.getState() + " won!");
			}
		}
		return gameState.getState();
	}
	
	public static GameState playGame(GameState gameState) throws YavalathException {
		
		
		while(gameState.getPlayingPlayer() != -1) {
							
				if(debug)
					System.out.println("GameState: " + gameState.getState() + 
							"\nNumber of Moves: " + gameState.getNumberOfMoves() + 
							"\nPlayer's Turn: " + gameState.getPlayingPlayer());
				
				if(debug || !(gameState.getPlayer()[gameState.getPlayingPlayer()-1] instanceof RandomAI))
					drawBoard(gameState.getBoard());
				gameState.playMove(-1);

				if(debug)
					drawBoard(gameState.getBoard());
			}
		return gameState;
	}
	
	private static void drawBoard(int[][] board){
		for(int i = 0; i < 9; i++){
			if(i >= 5 ){
				for(int b = 0; b <= i-5;b++)
					System.out.print(" ");
			}
			for(int j = 0; j < 9; j++){
				if(board[i][j] == -1) {
					System.out.print(" ");
				}else{
					System.out.print(board[i][j]+" ");
				}
			}	
			System.out.println();
		}
	}

	public static boolean getDebug(){
		return debug;
	}
	
	/**
	 * @param args
	 * @throws YavalathException 
	 */
	
	
	public static void startNewGame() throws YavalathException{
		plgnd = new PlayGround();
		debug = false;
		int percent = 0;
		long time = System.nanoTime();


		int[] players = mSettings.getPlayerInformation();
		System.out.println(players[0]);
		System.out.println(players[1]);
		System.out.println(players[2]);
		
		
		plgnd.showSettings(players);
		int numberOfGames = 1;	

		GameState.State[] games = new GameState.State[numberOfGames];
		
		for(int t = 0;t < numberOfGames; t++){
			games[t] = newGame(players);
			if(percent != (t*100)/numberOfGames){
				percent = (t*100)/numberOfGames;
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println(percent+"%");
			}
			}
		int d = 0, p1 = 0, p2 = 0, p3 = 0;
		for(int c = 0; c < games.length; c++){
			switch(games[c]){
				case DRAW:
					d++;
					break;
				case PLAYER1WIN:
					p1++;
					break;
				case PLAYER2WIN:
					p2++;
					break;
				case PLAYER3WIN:
					p3++;
					break;
			default:
				throw new YavalathException("unknown WinState!");
			}
		}
		
		time = System.nanoTime() -time ;
		time /= 1000000000;
		System.out.println("----- i = "+numberOfGames+" ------ "+time/60+":"+time%60+" Min--------");
		System.out.println(d+"/"+p1+"/"+p2+"/"+p3);
		System.out.println((d*100)/numberOfGames+"%/"
							+(p1*100)/numberOfGames+"%/"
							+(p2*100)/numberOfGames+"%/"
							+(p3*100)/numberOfGames+"%"); 
	}
	
	public static void showSettings(){
		SettingsGui settingsGui = new SettingsGui(mSettings);
	}
	
	
	
	public static void main(String[] args) throws YavalathException {
		mSettings = new Settings();
		MenueFactory menu = new MenueFactory();
	}
}