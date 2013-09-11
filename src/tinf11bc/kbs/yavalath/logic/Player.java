/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	public int makeMove(int[][] board) throws YavalathException{
		int move = forcedMove(board);
//		if(move != 0)
//			return move;
		
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    
		String eingabe = null;
		try {
			System.out.println("Player "+playerNumber+" mache zug:");
			eingabe = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		move = Integer.parseInt(eingabe);
		  
		return move;
	}
	
	protected int forcedMove(int[][]board) throws YavalathException{
		int x = 0;
		int y = 0;
		int z = 0;
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 6; i++){
				if(board[j][i] > 0){
					x = board[j][i];
					if(board[j][i+1] == 0 && board[j][i+2] == x && board[j][i+3] == x)
						return j*10+i+1;
					if(board[j][i+1] == x && board[j][i+2] == 0 && board[j][i+3] == x)
						return j*10+i+2;
					
					y = board[i][j];
					if(board[i+1][j] == 0 && board[i+2][j] == y && board[i+3][j] == y)
						return (i+1)*10+j;
					if(board[i+1][j] == y && board[i+2][j] == 0 && board[i+3][j] == y)
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
						z = board[t][r];
						if(board[t+1][r-1] == 0 && z == board[t+2][r-2] && z == board[t+3][r-3])
							return (t+1)*10+(r-1);
						if(board[t+2][r-2] == 0 && z == board[t+1][r-1] && z == board[t+3][r-3])
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
