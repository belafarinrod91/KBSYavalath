/**
 * 
 */
package tinf11bc.kbs.yavalath.logic.player;

import java.util.Random;

import tinf11bc.kbs.yavalath.logic.Yavalath;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.util.GameState;

/**
 * @author Stephan
 *
 */
public class RandomAI extends Player {

	private Random rand = new Random(System.nanoTime());
	
	public RandomAI(int playerNumber) {
		super(playerNumber);
	}

	@Override
	public int makeMove(GameState gameState) throws YavalathException{
		
		int move = forcedMove(gameState.getBoard());
		if(move != 0){
			if(Yavalath.getDebug())
				System.out.println("AI Forced Move: " + move);
			return move;
		}
		
		GameState tempGameState;
		GameState.State state;
		int cnt = 0;
		do{
			tempGameState = new GameState(gameState);
			
			do {
				move = randomMove(gameState.getBoard());
			} while (gameState.getBoard()[move/10][move%10] != 0);
			
			tempGameState.playMove(move);
			state = tempGameState.getState();
			
			if(cnt >= 10) {
				break;
			}
			
			switch(getPlayerNumber()) {
			case 1:
				if(state == GameState.State.PLAYER1OUT || state == GameState.State.PLAYER2WIN || state == GameState.State.PLAYER3WIN) {
					cnt++;
					move = -1; // try again
				}
				break;
			case 2:
				if(state == GameState.State.PLAYER2OUT || state == GameState.State.PLAYER1WIN || state == GameState.State.PLAYER3WIN) {
					cnt++;
					move = -1; // try again
				}
				break;
			case 3:
				if(state == GameState.State.PLAYER3OUT || state == GameState.State.PLAYER1WIN || state == GameState.State.PLAYER2WIN) {
					cnt++;
					move = -1; // try again
				}
				break;
			}
		} while(move == -1);
		
		if(Yavalath.getDebug())
			System.out.println("AI Move: " + move);
		
		return move;
	}
	
	private int randomMove(int[][] board){
		return GameState.tiles[rand.nextInt(61)];
	}
	
}
