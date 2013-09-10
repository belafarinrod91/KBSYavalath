/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chris
 *
 */

public class Player {

int playerNumber;
	
	public Player(int playerNumber){
		this.playerNumber = playerNumber;
	}
	
	public int makeMove(int[][] board){
		int move = forcedMove(board);
		if(move != 0)
			return move;
		
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
	
	protected int forcedMove(int[][]board){
		int[] x = {0,0};
		int[] y	= {0,0};
		
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 9; i++){
				if(board[j][i] > 0){
					if(x[0] != board[j][i]){
						x[0] = board[j][i];
						x[1] = 1;
						if(i+3 < 9 && board[j][i+1] == 0 && x[0] == board[j][i+2] && x[0] == board[j][i+3])
							return j*10+i+1;
					}else{
						if(x[0] == board[j][i]){
							x[1]++;
							if(i+2 < 9 && board[j][i+1] == 0 && x[0] == board[j][i+2])
								return j*10+i+1;
						}else{
							x[0] =0;
						}
					}	
				}else{
					x[1]=0;
				}
				
				if(board[i][j] > 0){
					if(y[0] != board[i][j]){
						y[0] = board[i][j];
						y[1] = 1;
						if(i+3 < 9 && board[i+1][j] == 0 && y[0] == board[i+2][j] && y[0] == board[i+3][j])
							return (i+1)*10+j;
					}else{
						if(y[0] == board[i][j]){
							y[1]++;
							if(i+2 < 9 && board[i+1][j] == 0 && y[0] == board[i+2][j])
								return (i+1)*10+j;
						}else{
							y[0] =0;
						}
					}
						
				}else{
					y[1]=0;
				}
				
			}
			int t = 0,r = j;
			if(r >= 4 && r <= 12){	
				if(r > 8){
					t = r - 8;
					r = 8;
				}else{
					t = 0;
				}
				try{
					for(; t < 8 ; t++,r--){
						if(board[t][r] > 0){
							if(board[t][r] == board[t+1][r-1] &&
								board[t][r] == board[t+3][r-3])
									return (t+2)*10+r-2;
							if(board[t][r] == board[t+2][r-2] &&
								board[t][r] == board[t+3][r-3])
									return (t+1)*10+r-1;
						}
					}
				}catch(Exception e){
					continue;
				}
			}
		}
		
		return 0;
		
	}
}
