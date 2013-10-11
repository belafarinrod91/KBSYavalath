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
	
	public static int[] numberOfSimulations = new int[3];


	
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
				
				if(debug)//|| !(gameState.getPlayer()[gameState.getPlayingPlayer()-1] instanceof RandomAI))
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
		numberOfSimulations[0] = mSettings.getDifficultyUTCAiOne();
		numberOfSimulations[1] = mSettings.getDifficultyUTCAiTwo();
		numberOfSimulations[2] = mSettings.getDifficultyUTCAiThree();
		
		plgnd = new PlayGround();
		debug = false;

		int[] players = mSettings.getPlayerInformation();
		
		plgnd.showSettings(players);
		
		newGame(players);
	}
	
	public static void showSettings(){
		SettingsGui settingsGui = new SettingsGui(mSettings);
	}
	
	
	
	public static void main(String[] args) throws YavalathException {
		mSettings = new Settings();
		MenueFactory menu = new MenueFactory();
	}
}