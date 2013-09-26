/**
 * 
 */
package tinf11bc.kbs.yavalath.logic.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tinf11bc.kbs.yavalath.logic.Yavalath;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.util.GameState;

/**
 * @author Chris, Stephan
 *
 */

public class Player {

	private int playerNumber;
	
	public Player(int playerNumber){
		this.playerNumber = playerNumber;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public int makeMove(GameState gameState) throws YavalathException {
		int move = forcedMove(gameState);
//		if(move != -1) {
//			System.out.println("Forced Move: " + move);
//			return move;
//		}
					
//	    InputStreamReader isr = new InputStreamReader(System.in);
//	    BufferedReader br = new BufferedReader(isr);
//	    
//		String eingabe = null;
//		try {
//			if(Yavalath.getDebug())
//				System.out.println("Player "+playerNumber+" mache zug:");
//			eingabe = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		move = Integer.parseInt(eingabe);
		  
		return move;
	}
	
	protected int forcedMove(GameState gameState) throws YavalathException{
		int[][] board = gameState.getBoard();
		int playerNumber = this.playerNumber;
		int nextPlayer = gameState.getNextPlayer();
		int move = -1;
		
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 6; i++){
				if(board[j][i] == playerNumber || board[j][i] == nextPlayer){
					if(board[j][i+1] == 0 
					&& board[j][i+2] == board[j][i] 
					&& board[j][i+3] == board[j][i]) {
						if(board[j][i] == playerNumber) {
							return (j*10)+(i+1);
						}
						move = (j*10)+(i+1);
					}
					if(board[j][i+1] == board[j][i] 
					&& board[j][i+2] == 0 
					&& board[j][i+3] == board[j][i]){
						if(board[j][i] == playerNumber) {
							return (j*10)+(i+2);
						}
						move = (j*10)+(i+2);
					}
						
				}
				if(board[i][j] == playerNumber || board[i][j] == nextPlayer){
					if(board[i+1][j] == 0 
					&& board[i+2][j] == board[i][j] 
					&& board[i+3][j] == board[i][j]) {
						if(board[i][j] == playerNumber) {
							return (i+1)*10+j;
						}
						move = (i+1)*10+j;
					}
					if(board[i+1][j] == board[i][j] 
					&& board[i+2][j] == 0 
					&& board[i+3][j] == board[i][j]) {
						if(board[i][j] == playerNumber) {
							return (i+2)*10+j;
						}
						move = (i+2)*10+j;
					}
				}
			}
			int t = 0,r = j+4;
			if(r > 8){
				t = r - 8;
				r = 8;
			}
			try{
				for(; t+3 <= 8 && r-3 >= 0; t++,r--){
					if(board[t][r] == playerNumber || board[t][r] == nextPlayer){
						if(board[t+1][r-1] == 0 &&
							board[t][r] == board[t+2][r-2] &&
							board[t][r] == board[t+3][r-3]) {
							if(board[t][r] == playerNumber) {
								return (t+1)*10+(r-1);
							}
							move = (t+1)*10+(r-1);
						}
						if(board[t+2][r-2] == 0 &&
							board[t][r] == board[t+1][r-1] && 
							board[t][r] == board[t+3][r-3]) {
							if(board[t][r] == playerNumber) {
								return (t+2)*10+(r-2);
							}
							move = (t+2)*10+(r-2);
						}
					}
				}
			}catch(Exception e){
				System.err.println("Error in Win/Lose detection (diagonal)!");
				throw new YavalathException();
			}
		}
		return move;
		
	}
}
