/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.util.Random;

import tinf11bc.kbs.yavalath.util.Board;

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
	public int makeMove(int[][] board) throws YavalathException{
		int move = forcedMove(board);
		if(move != 0){
			System.out.println("AI Forced Move: " + move);
			return move;
		}
		
		do{
			move = randomMove(board);
		} while(board[move/10][move%10] != 0);
		
		System.out.println("AI Move: " + move);
		
		return move;
	}
	
	private int randomMove(int[][] board){
		return Board.tiles[rand.nextInt(61)];
	}
	
}
