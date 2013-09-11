/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.util.Random;

/**
 * @author Stephan
 *
 */
public class AI extends Player {

	private Random rand = new Random(System.nanoTime());
	private int[] tiles = {4,5,6,7,8,
						13,14,15,16,17,18,
					  22,23,24,25,26,27,28,
					 31,32,33,34,35,36,37,38,
					40,41,42,43,44,45,46,47,48,
					 50,51,52,53,54,55,56,57,
					  60,61,62,63,64,65,66,
					   70,71,72,73,74,75,
						 80,81,82,83,84};
	
	public AI(int playerNumber) {
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
		return tiles[rand.nextInt(61)];
	}
	
}
