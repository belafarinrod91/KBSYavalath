package tinf11bc.kbs.yavalath.logic;

import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.logic.player.RandomAI;
import tinf11bc.kbs.yavalath.util.GameState;
import tinf11bc.kbs.yavalath.util.GameState.State;

/**
 * @author Chris, Stephan
 *
 */
public class Yavalath {
	
	private static boolean debug = false;
	public static int numberOfSimulations = 100000;
	
	public static State newGame(int[] newPlayers) throws YavalathException {

		GameState gameState = new GameState(newPlayers);
		
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
			for(int j = 0; j < 9; j++){
				if(board[i][j] == -1) {
					System.out.print("  ");
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
	public static void main(String[] args) throws YavalathException {
		int i = 1;
		debug = false;
		GameState.State[] games = new GameState.State[i];
		int percent = 0;
		long time = System.nanoTime();;

		//menu with choosing:
		//					-how many players? (2/3)
		//					-player/AI 
			
		int[] players = {3,3,0};	// Player: 1
									// RandomAI: 2
									// UCTAI: 3
			
		for(int t = 0;t < i; t++){

			games[t] = newGame(players);
			if(percent != (t*100)/i){
				percent = (t*100)/i;
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
		System.out.println("----- i = "+i+" ------ "+time/60+":"+time%60+" Min--------");
		i = i/100;
		System.out.println(d+"/"+p1+"/"+p2+"/"+p3);
		System.out.println(d/i+"%/"+p1/i+"%/"+p2/i+"%/"+p3/i+"%");
	}
}