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
		int move = forcedMove(gameState.getBoard());
//		if(move != 0)
//			return move;
		
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    
		String eingabe = null;
		try {
			if(Yavalath.getDebug())
				System.out.println("Player "+playerNumber+" mache zug:");
			eingabe = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		move = Integer.parseInt(eingabe);
		  
		return move;
	}
	
	protected int forcedMove(int[][]board) throws YavalathException{
		int playerNumber = 0;
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 6; i++){
				if(board[j][i] > 0){
					playerNumber = board[j][i];
					if(board[j][i+1] == 0 
					&& board[j][i+2] == playerNumber 
					&& board[j][i+3] == playerNumber)
						return (j*10)+(i+1);
					if(board[j][i+1] == playerNumber 
					&& board[j][i+2] == 0 
					&& board[j][i+3] == playerNumber)
						return (j*10)+(i+2);
				}
				if(board[i][j] > 0){
					playerNumber = board[i][j];
					if(board[i+1][j] == 0 
					&& board[i+2][j] == playerNumber 
					&& board[i+3][j] == playerNumber)
						return (i+1)*10+j;
					if(board[i+1][j] == playerNumber 
					&& board[i+2][j] == 0 
					&& board[i+3][j] == playerNumber)
						return (i+2)*10+j;
				}
			}
			int t = 0,r = j+4;
			if(r > 8){
				t = r - 8;
				r = 8;
			}
			try{
				for(; t < 8 && r-2 >= 0; t++,r--){
					if(r-3 >=0 && t+3 < 8 && board[t][r] > 0){
						playerNumber = board[t][r];
						if(board[t+1][r-1] == 0 &&
			   playerNumber == board[t+2][r-2] &&
			   playerNumber == board[t+3][r-3])
							return (t+1)*10+(r-1);
						if(board[t+2][r-2] == 0 &&
			   playerNumber == board[t+1][r-1] && 
			   playerNumber == board[t+3][r-3])
							return (t+2)*10+(r-2);
					}
				}
			}catch(Exception e){
				System.err.println("Error in Win/Lose detection (diagonal)!");
				throw new YavalathException();
			}
		}
		return 0;
		
	}
}
