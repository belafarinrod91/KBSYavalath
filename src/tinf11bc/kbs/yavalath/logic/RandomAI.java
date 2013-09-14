/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.util.Random;

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
		
		do{
			move = randomMove(gameState.getBoard());
		} while(gameState.getBoard()[move/10][move%10] != 0);
		
		if(Yavalath.getDebug())
			System.out.println("AI Move: " + move);
		
		return move;
	}
	
	private int randomMove(int[][] board){
		return GameState.tiles[rand.nextInt(61)];
	}
	
}
